package ms.math.application.response.interceptor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

public class CachedBodyHttpServletResponse extends HttpServletResponseWrapper {

   private final ByteArrayOutputStream cachedContent = new ByteArrayOutputStream();

   private ServletOutputStream outputStream;

   private PrintWriter writer;

   private boolean committed = false;

   public CachedBodyHttpServletResponse(HttpServletResponse response) {
      super(response);
   }

   @Override
   public ServletOutputStream getOutputStream() throws IOException {
      if (this.writer != null) {
         throw new IllegalStateException("Ya se ha llamado a getWriter() en esta respuesta.");
      }

      if (this.outputStream == null) {
         this.outputStream = new CachedServletOutputStream(this.cachedContent, super.getOutputStream());
      }
      return this.outputStream;
   }

   @Override
   public PrintWriter getWriter() throws IOException {
      if (this.outputStream != null) {
         throw new IllegalStateException("Ya se ha llamado a getOutputStream() en esta respuesta.");
      }

      if (this.writer == null) {
         this.writer = new PrintWriter(new OutputStreamWriter(getOutputStream(), getCharacterEncoding()));
      }
      return this.writer;
   }

   @Override
   public void flushBuffer() throws IOException {
      if (writer != null) {
         writer.flush();
      } else if (outputStream != null) {
         outputStream.flush();
      }
      committed = true;
      super.flushBuffer();
   }

   @Override
   public boolean isCommitted() {
      return committed || super.isCommitted();
   }

   public byte[] getCachedContent() {
      if (writer != null) {
         writer.flush();
      }
      return this.cachedContent.toByteArray();
   }

   private static class CachedServletOutputStream extends ServletOutputStream {

      private final ByteArrayOutputStream cache;

      private final ServletOutputStream original;

      public CachedServletOutputStream(ByteArrayOutputStream cache, ServletOutputStream original) {
         this.cache = cache;
         this.original = original;
      }

      @Override
      public void write(int b) throws IOException {
         cache.write(b);
         original.write(b);
      }

      @Override
      public void write(byte[] b) throws IOException {
         cache.write(b);
         original.write(b);
      }

      @Override
      public void write(byte[] b, int off, int len) throws IOException {
         cache.write(b, off, len);
         original.write(b, off, len);
      }

      @Override
      public void flush() throws IOException {
         cache.flush();
         original.flush();
      }

      @Override
      public void close() throws IOException {
         cache.close();
         original.close();
      }

      @Override
      public boolean isReady() {
         return original.isReady();
      }

      @Override
      public void setWriteListener(WriteListener writeListener) {
         original.setWriteListener(writeListener);
      }
   }
}
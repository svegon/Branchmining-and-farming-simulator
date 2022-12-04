package branchmining.and.farming.simulator.util.crash;

import com.google.common.collect.Maps;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CrashException extends RuntimeException {
    private final Map<String, List<String>> sections = Maps.newLinkedHashMap();
    private final Thread causeThread;

    public CrashException(Thread causeThread, Throwable cause) {
        super(cause);
        this.causeThread = causeThread;
    }

    public CrashException(Throwable cause) {
        this(Thread.currentThread(), cause);
    }

    public void logException(Logger logger) {
        logger.log(Level.SEVERE, "A fatal exception has occurred in thread " + causeThread + ".");
        printStackTrace(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                byte[] a;

                if ((b & 0xFF000000) != 0) {
                    a = new byte[]{(byte) (b >>> 24), (byte) (b >>> 16), (byte) (b >>> 8), (byte) b};
                } else if ((b & 0xFFFF0000) != 0) {
                    a = new byte[]{(byte) (b >>> 16), (byte) (b >>> 8), (byte) b};
                } else if ((b & 0xFFFFFF00) != 0) {
                    a = new byte[]{(byte) (b >>> 8), (byte) b};
                } else {
                    a = new byte[]{(byte) b};
                }

                write(a, 0, 1);
            }

            @Override
            public void write(@NotNull byte[] b, int off, int len) throws IOException {
                logger.log(Level.SEVERE, "\t" + new String(b));
            }
        }));

        for (Map.Entry<String, List<String>> e : sections.entrySet()) {
            logger.log(Level.SEVERE, e.getKey());

            for (String log : e.getValue()) {
                logger.log(Level.SEVERE, "\t" + log);
            }
        }
    }
}

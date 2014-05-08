package vottie.imgmanager.util;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter {

	@Override
	public String format(LogRecord record) {
		final StringBuffer message = new StringBuffer(131);
		
		long millis = record.getMillis();
		String time = String.format("%tY/%<tm/%<td %<tT.%<tL ", millis);
		message.append(time);
		String msg = String.format("[%5d] %c: %s",
				record.getThreadID(),
				record.getLevel().toString().charAt(0),
				formatMessage(record));
        message.append(msg);
        message.append('\n');
        Throwable throwable = record.getThrown();
        if (throwable != null) {
            message.append(throwable.toString());
            message.append('\n');
            for (StackTraceElement trace : throwable.getStackTrace()) {
                message.append('\t');
                message.append(trace.toString());
                message.append('\n');
            }
        }
        return message.toString();
	}
}

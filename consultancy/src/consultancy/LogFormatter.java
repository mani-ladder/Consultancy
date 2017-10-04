package consultancy;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter
{
	private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy' ['hh:mm:ss:SSSS']'");
	StringBuilder stringBuilder = new StringBuilder(1000);
	
	public String format(LogRecord record)
	{
		stringBuilder.append(dateFormat.format(new Date(record.getMillis())));
		stringBuilder.append(" - ");
		stringBuilder.append("[ ").append(record.getSourceClassName()).append(" ]");
		stringBuilder.append(" - ");
		stringBuilder.append(record.getLevel());
		stringBuilder.append(" - ");
		stringBuilder.append(formatMessage(record));
		stringBuilder.append("\n\n");
		return stringBuilder.toString();
	}
	
}

package consultancy;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class AddUser extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(AddUser.class.getName());
	FileHandler fileHandler = null;
	String phoneNumber = null;
	Boolean isPresent = true;
	Boolean success = false;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	// Response and Request
	{

		try
		{
			fileHandler = new FileHandler("consultancy.log", true);
			log.addHandler(fileHandler);
			fileHandler.setFormatter(new LogFormatter());
		} catch (IOException e)
		{
			System.out.println("IOException");
			e.printStackTrace();
		}

		// getting PhoneNumber from parameter
		phoneNumber = request.getAttribute(phoneNumber).toString();

		// checking the phoneNumber whether it is exist already
		isPresent = CheckingNumber(phoneNumber);

		if (!isPresent)
		{
			if (ServletFileUpload.isMultipartContent(request))
			// parsing the request and checking file is multi-part
			{
				try
				{
					@SuppressWarnings("unchecked")
					List<FileItem> multiPartFile = new ServletFileUpload(new DiskFileItemFactory())
							.parseRequest(request);

					for (FileItem item : multiPartFile)
					{
						if (!item.isFormField())
						{
							UploadToDatabase upload = new UploadToDatabase(item, phoneNumber);
							try
							{
								success = upload.uploadNow();
							} catch (NoSuchAlgorithmException | InstantiationException
									| IllegalAccessException | ClassNotFoundException
									| SQLException e)
							{
								e.printStackTrace();
							}
						}
					}
				} catch (FileUploadException e)
				{
					System.out.println("FileUploadException!");
					request.setAttribute("message", "Document Uploaded Failed!" + e);
					e.printStackTrace();
				}
			}
			if (success)
			{
				request.setAttribute("message", "Document Uploaded Successfully");
			}
			else
			{
				request.setAttribute("message", "Document Uploaded Failed!");
			}
		}
		else
		{
			request.setAttribute("message",
					"Document Uploaded Failed! Phone Number already registered!");
		}
	}

	private Boolean CheckingNumber(String phoneNumber)
	{
		CheckUser checkUser;

		try
		{
			checkUser = new CheckUser(phoneNumber);
			return checkUser.isUserPresent();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException
				| SQLException | SecurityException | IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

}

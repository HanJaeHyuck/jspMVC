package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BoardDAO;
import vo.BoardVO;

public class ViewController implements Controller {

	@Override
	public String service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		//Passing parameters
		int id = Integer.parseInt(req.getParameter("id"));
		BoardVO data = BoardDAO.instance.view(id);
		req.setAttribute("data", data);
		return "view";
	}

}

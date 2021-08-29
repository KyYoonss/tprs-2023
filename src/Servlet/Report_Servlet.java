package Servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.text.*;
import java.util.*;

import DAO.Equipment_DAO;
import DAO.Equipment_Type_DAO;
import DAO.Supplier_DAO;

@WebServlet("/Report_Servlet")
public class Report_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Equipment_DAO equipmentdao;
	private Equipment_Type_DAO equipmenttypedao;
	private Supplier_DAO supplierdao;
	
    public Report_Servlet() {
        super();
        equipmentdao = new Equipment_DAO();
        equipmenttypedao = new Equipment_Type_DAO();
        supplierdao = new Supplier_DAO();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filter_type = request.getParameter("filter_type");
		String filter_value = null;
		
		if(filter_type.equalsIgnoreCase("Equipment Type")) {
			filter_value = request.getParameter("eqtype_idnum");
			request.setAttribute("equipmentreportlist", equipmentdao.viewequipmentreport(filter_type, filter_value));
		}		
		else if(filter_type.equalsIgnoreCase("Status")) {
			filter_value = request.getParameter("equipment_status");
			request.setAttribute("equipmentreportlist", equipmentdao.viewequipmentreport(filter_type, filter_value));
		}		
		else if(filter_type.equalsIgnoreCase("Department")) {
			filter_value = request.getParameter("department_idnum");
			request.setAttribute("equipmentreportlist", equipmentdao.viewequipmentreport(filter_type, filter_value));
		}		
		else if(filter_type.equalsIgnoreCase("Location")) {
			filter_value = request.getParameter("location_idnum");
			request.setAttribute("equipmentreportlist", equipmentdao.viewequipmentreport(filter_type, filter_value));
		}		
		else if(filter_type.equalsIgnoreCase("Supplier")) {
			filter_value = request.getParameter("supplier_idnum");
			request.setAttribute("equipmentreportlist", equipmentdao.viewequipmentreport(filter_type, filter_value));
		}		
		else if(filter_type.equalsIgnoreCase("No Filter")) {
			request.setAttribute("equipmentreportlist", equipmentdao.viewequipmentreport(filter_type, filter_value));
		}
		
		request.setAttribute("table_status", filter_type);
		request.setAttribute("graph_type", Boolean.parseBoolean(request.getParameter("graph_type")));
		request.setAttribute("graph_status", Boolean.parseBoolean(request.getParameter("graph_status")));
		request.setAttribute("graph_brand", Boolean.parseBoolean(request.getParameter("graph_brand")));
		request.setAttribute("graph_supplier", Boolean.parseBoolean(request.getParameter("graph_supplier")));
		
		SimpleDateFormat dayformat = new SimpleDateFormat("EEEE");
		SimpleDateFormat dateformat = new SimpleDateFormat("MMM dd, yyyy");
		SimpleDateFormat timeformat = new SimpleDateFormat("hh:mm aa");
		Date date = new Date();
		
		request.setAttribute("reportdate", dayformat.format(date) + " " + dateformat.format(date));
		request.setAttribute("reporttime", timeformat.format(date).toString().toUpperCase());
		request.setAttribute("reportfilter", filter_type + " - " + filter_value);
		request.setAttribute("reportsemester", request.getParameter("semester"));
		
		request.setAttribute("equipmenttypegraph", equipmenttypedao.viewequipmenttypetotal(false));
		request.setAttribute("equipmentstatusgraph", equipmentdao.viewequipmentstatustotal(false));
		request.setAttribute("equipmentbrandgraph", equipmentdao.viewequipmentbrandtotal());
		request.setAttribute("suppliergraph", supplierdao.viewsuppliertotal());
		
		RequestDispatcher view = request.getRequestDispatcher("Staff_report_page.jsp");
		view.forward(request, response);
	}
}
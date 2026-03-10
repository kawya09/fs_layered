package lk.ijse.demo.bo.custom.impl;

import lk.ijse.demo.bo.custom.DashboardBO;
import lk.ijse.demo.dao.DAOFactory;
import lk.ijse.demo.dao.custom.CustomerDAO;
import lk.ijse.demo.dao.custom.DashboardDAO;

public class DashboardBOImpl implements DashboardBO {
    DashboardDAO customerDAO = (DashboardDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.DASHBOARD) ;


}

package lk.ijse.demo.bo.custom.impl;

import lk.ijse.demo.bo.custom.SalesOrderItemBO;
import lk.ijse.demo.dao.DAOFactory;

public class SalesOrderItemBOImpl implements SalesOrderItemBO {
    SalesOrderItemDAO salesOrderItemDAO = (SalesOrderItemDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SALERSORDERITEM);
}

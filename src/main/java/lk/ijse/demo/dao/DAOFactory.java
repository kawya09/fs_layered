package lk.ijse.demo.dao;

import lk.ijse.demo.dao.custom.CategoryDAO;

import lk.ijse.demo.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private  DAOFactory() {
    }
    public static DAOFactory getInstance() {
        return daoFactory==null?daoFactory=new DAOFactory():daoFactory;
    }
    public enum DAOType{
        CATEGORY,CASTOMER,DELIVERY,ITEM,PAYMENT,SALESORDER,SALERSORDERITEM,SUPPLIER
    }
    public SuperDAO getDAO(DAOType type){
        switch (type){
            case CATEGORY:
                return new CategoryDAOImpl();
                case CASTOMER:
                    return new CustomerDAOImpl();
                    case DELIVERY:
                        return new DeliveryDAOImpl();
                        case ITEM:
                            return new ItemDAOImpl();
                            case PAYMENT:
                                return new PaymentDAOImpl();
                                case SALESORDER:
                                    return new SalesOrderDAOImpl();

                                        case SUPPLIER:
                                            return new SupplierDAOImpl();
            default:
                return null;
        }

    }

}

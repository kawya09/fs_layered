package lk.ijse.demo.bo;

import lk.ijse.demo.bo.custom.impl.*;

import static lk.ijse.demo.dao.DAOFactory.DAOType.SUPPLIER;

public class BOFactory {
    private static BOFactory instance;

    private  BOFactory(){
    }
    public static BOFactory getInstance(){
        return instance == null ? instance = new BOFactory() : instance;
    }

    public enum BOType{
        Category,
        CUSTOMER,
        DELIVERY,
        DASHBOARD,
        ITEM,
        PAYMENT,
        SALESORDER,
        SALESORDERITEM,
        SAPPLIER
    }
    public SuperBO getBO(BOType type)  {
        switch (type){
            case Category:
                return new CategoryBOImpl();
            case CUSTOMER:
                return new CustomerBOImpl();
            case DELIVERY:
                return new DeliveryBOImpl();
            case DASHBOARD:
                return new DashboardBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            case SALESORDER:
                return new SalesOrderBOImpl();
            case SALESORDERITEM:
                return new SalesOrderItemBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
                default:
                    return null;
        }
    }
}

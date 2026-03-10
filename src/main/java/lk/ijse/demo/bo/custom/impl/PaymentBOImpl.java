package lk.ijse.demo.bo.custom.impl;

import lk.ijse.demo.bo.custom.PaymentBO;
import lk.ijse.demo.dao.DAOFactory;
import lk.ijse.demo.dao.custom.CustomerDAO;
import lk.ijse.demo.dao.custom.PaymentDAO;
import lk.ijse.demo.dto.CustomerDTO;
import lk.ijse.demo.dto.PaymentDTO;
import lk.ijse.demo.entity.Customer;
import lk.ijse.demo.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentBOImpl implements PaymentBO {
    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);

    public List<PaymentDTO> getAllPayment() throws SQLException {
        List<Payment> entities = paymentDAO.getAll();
        List<PaymentDTO> list = new ArrayList<>();

        for(Payment entity : entities){
            list.add(new PaymentDTO(entity.getPaymentId(), entity.getOrderId(), entity.getPaymentDate(), entity.getAmount(), entity.getMethod(),entity.getStatus(),entity.getReferenceNo(),entity.getNotes()));
        }

        return list;
    }

    public  boolean savePayment(PaymentDTO dto) throws SQLException {

        return paymentDAO.save(new Payment(dto.getPaymentId(),dto.getOrderId(), dto.getPaymentDate(), dto.getAmount(), dto.getMethod(),dto.getStatus(),dto.getReferenceNo(),dto.getNotes()));
    }


}

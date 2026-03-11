package lk.ijse.demo.bo.custom;

import lk.ijse.demo.bo.SuperBO;
import lk.ijse.demo.dao.CrudUtil;
import lk.ijse.demo.dto.PaymentDTO;
import lk.ijse.demo.entity.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PaymentBO extends SuperBO {
    public List<PaymentDTO> getAllPayment() throws SQLException ;

    public  boolean savePayment(PaymentDTO dto) throws SQLException ;






}

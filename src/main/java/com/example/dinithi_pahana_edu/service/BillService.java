package com.example.dinithi_pahana_edu.service;

import com.example.dinithi_pahana_edu.dao.BillDAO;
import com.example.dinithi_pahana_edu.model.Bill;
import com.example.dinithi_pahana_edu.model.BillItem;
import java.util.List;

public class BillService {
    private BillDAO billDAO = new BillDAO();

    public int addBill(Bill bill) {
        return billDAO.addBill(bill);
    }

    public boolean addBillItems(List<BillItem> items) {
        return billDAO.addBillItems(items);
    }

    public int getNextBillNumber() {
        return billDAO.getMaxBillNumber() + 1;
    }
    
    public List<Bill> getBillsByCustomerId(int customerId) {
        return billDAO.getBillsByCustomerId(customerId);
    }
    
    public List<BillItem> getBillItemsByBillId(int billId) {
        return billDAO.getBillItemsByBillId(billId);
    }
} 
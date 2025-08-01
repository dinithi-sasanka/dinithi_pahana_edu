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

    public List<Bill> getAllBills() {
        return billDAO.getAllBills();
    }

    public List<Bill> searchBills(String searchTerm, String fromDate, String toDate) {
        return billDAO.searchBills(searchTerm, fromDate, toDate);
    }

    public boolean updateBill(Bill bill) {
        return billDAO.updateBill(bill);
    }
    public boolean deleteBillItemsByBillId(int billId) {
        return billDAO.deleteBillItemsByBillId(billId);
    }

    public boolean deleteBillById(int billId) {
        return billDAO.deleteBillById(billId);
    }

    public List<Object[]> getTopCustomers(int limit) {
        return billDAO.getTopCustomers(limit);
    }
    public List<Object[]> getMostSoldItems(int limit) {
        return billDAO.getMostSoldItems(limit);
    }
    public List<Object[]> getDailySales(int days) {
        return billDAO.getDailySales(days);
    }
    public List<Object[]> getMonthlySales(int months) {
        return billDAO.getMonthlySales(months);
    }
} 
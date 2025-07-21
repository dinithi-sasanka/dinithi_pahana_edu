package com.example.dinithi_pahana_edu.service;

import com.example.dinithi_pahana_edu.dao.ItemDAO;
import com.example.dinithi_pahana_edu.model.Item;

public class ItemService {
    private ItemDAO itemDAO = new ItemDAO();

    public boolean addItem(Item item) {
        return itemDAO.addItem(item);
    }

    public java.util.List<Item> getAllItems() {
        return itemDAO.getAllItems();
    }

    public java.util.List<Item> getFilteredItems(String name, String category, Double minPrice, Double maxPrice) {
        return itemDAO.getFilteredItems(name, category, minPrice, maxPrice);
    }

    public Item getItemById(int id) {
        return itemDAO.getItemById(id);
    }

    public boolean updateItem(Item item) {
        return itemDAO.updateItem(item);
    }

    public boolean deleteItem(int id) {
        return itemDAO.deleteItem(id);
    }

    public java.util.List<Item> getLowStockItems(int threshold) {
        return itemDAO.getLowStockItems(threshold);
    }

    public boolean decrementStock(int itemId, int quantity) {
        return itemDAO.decrementStock(itemId, quantity);
    }

    public java.util.List<Item> getAllItemsWithCurrentStock() {
        return itemDAO.getAllItemsWithCurrentStock();
    }
} 
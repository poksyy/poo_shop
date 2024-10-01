package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import dao.Dao;
import dao.DaoImplFile;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import main.Shop;
import model.Product;

public class ExportInventoryView extends JDialog implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Shop shop;
	public static Dao dao  = new DaoImplFile();

	public ExportInventoryView(Shop shop) {
		this.shop = shop;
		dao.writeInventory(null);
		exportUI();
	}

	/**
	 * Initializes and displays the inventory user interface.
	 */
	private void exportUI() {
	    javax.swing.JOptionPane.showMessageDialog(this, "Inventario exportado exitosamente.");
}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}

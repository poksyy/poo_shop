package dao;

import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import model.Employee;
import model.Product;
import model.ProductHistory;
import model.Sale;

public class DaoImplHibernate implements Dao {

	private SessionFactory sessionFactory;

	public DaoImplHibernate() {
		try {
			this.sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		} catch (Exception e) {
			System.err.println("Error initializing Hibernate: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void connect() {
	}

	@Override
	public void disconnect() {
		if (sessionFactory != null && !sessionFactory.isClosed()) {
			sessionFactory.close();
		}
	}

	@Override
	public Employee getEmployee(int id, String password) {
		return null;
	}

	@Override
	public ArrayList<Product> getInventory() {
		Transaction transaction = null;
		ArrayList<Product> products = new ArrayList<>();
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			products = new ArrayList<>(session.createQuery("FROM Product", Product.class).list());
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.err.println("Error getting inventory: " + e.getMessage());
			e.printStackTrace();
		}
		return products;
	}

	@Override
	public boolean writeInventory(ArrayList<Product> inventory) {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();

			for (Product product : inventory) {
				ProductHistory history = new ProductHistory();
				history.setIdProduct(product.getId());
				history.setName(product.getName());
				history.setPrice(product.getPrice());
				history.setStock(product.getStock());

				session.persist(history);
			}

			transaction.commit();
			System.out.println("Inventory exported successfully: " + inventory.size() + " products inserted.");
			return true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.err.println("Unable to export inventory: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void addProduct(Product product) {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			session.save(product);
			transaction.commit();
			System.out.println("Product added successfully: " + product);
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.err.println("Error adding product: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void updateProduct(Product product) {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			session.update(product);
			transaction.commit();
			System.out.println("Product updated successfully: " + product);
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.err.println("Error updating product: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void deleteProduct(Product product) {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			session.delete(product);
			transaction.commit();
			System.out.println("Product deleted successfully");
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.err.println("Error deleting product: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public Product getProduct(int id) {
		return null;
	}

	@Override
	public void addSale(Sale sale) {
	}
}

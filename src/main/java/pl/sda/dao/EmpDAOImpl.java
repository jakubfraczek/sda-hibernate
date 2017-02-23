package pl.sda.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import pl.sda.domain.Employee;

import java.math.BigDecimal;
import java.util.List;


/**
 * Created by pzawa on 02.02.2017.
 */
public class EmpDAOImpl implements EmpDAO {
	private final SessionFactory sessionFactory;

	public EmpDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Employee findById(int id) throws Exception {
		try (Session session = sessionFactory.openSession()) {
			return session.find(Employee.class, id);
		}
	}

	@Override
	public void create(Employee employee) throws Exception {
		Transaction tx = null;
		try (Session session = sessionFactory.openSession()) {
			tx = session.beginTransaction();
			session.persist(employee);
			tx.commit();
		} catch (Exception ex) {
			if (tx != null && !tx.getRollbackOnly()) {
				tx.rollback();
			}
			throw ex;
		}
	}

	@Override
	public void update(Employee employee) throws Exception {
		Transaction tx = null;
		try (Session session = sessionFactory.openSession()) {
			tx = session.beginTransaction();
			session.update(employee);
			tx.commit();
		} catch (Exception ex) {
			if (tx != null && !tx.getRollbackOnly()) {
				tx.rollback();
			}
			throw ex;
		}
	}

	@Override
	public void delete(int id) throws Exception {
		Transaction tx = null;
		try (Session session = sessionFactory.openSession()) {
			tx = session.beginTransaction();
			session.delete(session.find(Employee.class, id));
			tx.commit();
		} catch (Exception ex) {
			if (tx != null && !tx.getRollbackOnly()) {
				tx.rollback();
			}
			throw ex;
		}
	}

	@Override
	public void create(List<Employee> employees) throws Exception {
		Transaction tx = null;
		try (Session session = sessionFactory.openSession()) {
			tx = session.beginTransaction();
			for (Employee employee : employees){
				session.persist(employee);
			}
			tx.commit();
		} catch (Exception ex) {
			if (tx != null && !tx.getRollbackOnly()) {
				tx.rollback();
			}
			throw ex;
		}
	}

	@Override
	public BigDecimal getTotalSalaryByDept(int dept) throws Exception {
		Transaction tx = null;
		BigDecimal salary;
		try (Session session = sessionFactory.openSession()) {
			tx = session.beginTransaction();
			Query<BigDecimal> query = session.getNamedQuery("calculateSalaryByDept").setParameter("deptno", dept);
			salary = query.getSingleResult();
			tx.commit();
		} catch (Exception ex) {
			if (tx != null && !tx.getRollbackOnly()) {
				tx.rollback();
			}
			throw ex;
		}
		return salary;
	}

	@Override
	public List<Employee> getEmployeesByDept(int deptNo) {
		return null;
	}

	@Override
	public List<Employee> getEmployeeByName(String ename) {
		return null;
	}
}

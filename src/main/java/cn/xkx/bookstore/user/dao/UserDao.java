package cn.xkx.bookstore.user.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.jdbc.TxQueryRunner;
import cn.xkx.bookstore.book.domain.Book;
import cn.xkx.bookstore.book.domain.PageBean;
import cn.xkx.bookstore.category.domain.Category;
import cn.xkx.bookstore.user.domain.User;

/**
 * User持久层
 * 
 * @author XuKexiang
 *
 */
public class UserDao {
	private QueryRunner qr = new TxQueryRunner();

	/*
	 * 按用户名查询
	 */
	public User findByUsername(String username) {
		try {
			String sql = "select * from tb_user where username=?";
			return qr.query(sql, new BeanHandler<User>(User.class), username);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * 按邮箱查询
	 */
	public User findByEmail(String email) {
		try {
			String sql = "select * from tb_user where email=?";
			return qr.query(sql, new BeanHandler<User>(User.class), email);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/*
	 * 按激活码查询
	 */
	public User findByCode(String code) {
		try {
			String sql = "select * from tb_user where code=?";
			return qr.query(sql, new BeanHandler<User>(User.class), code);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/*
	 * 激活用户状态
	 */
	public void updateState(String uid, boolean state)
	{
		try {
			String sql = "update tb_user set state=? where uid=?";
			qr.update(sql,state,uid);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void add(User user) {
		try {	
			String sql = "INSERT INTO tb_user VALUES(?,?,?,?,?,?,?)";
			Object[] params = {user.getUid(),user.getUserType(),user.getUsername(), user.getPassword(), user.getEmail(), user.getCode(),
					user.isState() };
			qr.update(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public PageBean<User> findAllUsers(int pc, int ps) {
		String sql = "SELECT * FROM tb_user where userType='user' LIMIT ?,?";
		try {

			PageBean pb = new PageBean();
			pb.setPc(pc);
			pb.setPs(ps);
			
			// 查询总记录数tr
			String tr_sql = "SELECT COUNT(*) FROM tb_user where userType='user'";
			Number number = (Number) qr.query(tr_sql, new ScalarHandler());
			int tr = number.intValue();
			pb.setTr(tr);
			
			Object[] params = { ps * (pc - 1), ps };
			List<User> beanList = qr.query(sql, new BeanListHandler<User>(User.class), params);

			pb.setBeanList(beanList);

			return pb;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public boolean findUserByUsername(String username) {
		String sql = "SELECT * FROM tb_user where username=? and userType = ?";
		try {

			Object[] params = { username,"user" };
			User bean = qr.query(sql, new BeanHandler<User>(User.class), params);

			return bean!=null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public boolean findUserByEmail(String email) {
		String sql = "SELECT * FROM tb_user where email=?";
		try {
			Object[] params = { email};
			User bean = qr.query(sql, new BeanHandler<User>(User.class), params);
			return bean!=null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}

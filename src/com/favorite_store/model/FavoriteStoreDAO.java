package com.favorite_store.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.store_profile.model.StoreProfileVO;

public class FavoriteStoreDAO implements Favorite_StoreDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String UPDATE = "UPDATE FAVORITE_STORE SET IS_FAVO=? WHERE MEM_NUM=? AND STO_NUM=?";
	private static final String GET_FAVORITESTORES = "SELECT STO_NUM, STO_NAME, MOBILE, AREA, ADDRESS, STO_STATUS FROM STORE_PROFILE WHERE STO_NUM IN (SELECT DISTINCT STO_NUM FROM FAVORITE_STORE WHERE MEM_NUM=? AND IS_FAVO=?)";

	@Override
	public void update(FavoriteStoreVO favoritestoreVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			con.setAutoCommit(false);

			pstmt.setString(1, favoritestoreVO.getIs_favo());
			pstmt.setString(2, favoritestoreVO.getMem_num());
			pstmt.setString(3, favoritestoreVO.getSto_num());

			pstmt.executeUpdate();

			con.commit();

		} catch (SQLException se) {
			try {
				con.rollback();
				throw new RuntimeException("A database error occured. " + se.getMessage());
			} catch (SQLException see) {
				see.printStackTrace();
			}
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.setAutoCommit(true);
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public List<StoreProfileVO> getFavoriteStore(FavoriteStoreVO favoritestoreVO) {
		List<StoreProfileVO> list = new ArrayList<StoreProfileVO>();
		StoreProfileVO storeProfileVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_FAVORITESTORES);

			pstmt.setString(1, favoritestoreVO.getMem_num());
			pstmt.setString(2, favoritestoreVO.getIs_favo());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				storeProfileVO = new StoreProfileVO();
				storeProfileVO.setSto_num(rs.getString("STO_NUM"));
				storeProfileVO.setSto_name(rs.getString("STO_NAME"));
				storeProfileVO.setMobile(rs.getString("MOBILE"));
				storeProfileVO.setArea(rs.getString("AREA"));
				storeProfileVO.setAddress(rs.getString("ADDRESS"));
				storeProfileVO.setSto_status(rs.getString("STO_STATUS"));
				list.add(storeProfileVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	
}
package com.member_profile.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MemberProfileDAO implements MemberProfileDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String GET_MEMBERPROFILE = "SELECT * FROM MEMBER_PROFILE WHERE MEM_NUM=?";
	private static final String UPDATE = "UPDATE MEMBER_PROFILE SET MEM_NAME=NVL(?, MEM_NAME), MEM_PWD=NVL(?, MEM_PWD), MOBILE=NVL(?, MOBILE), EMAIL=NVL(?, EMAIL), ADDRESS=NVL(?, ADDRESS), MEM_IMG=NVL(?, MEM_IMG), REM_POINT=NVL(?, REM_POINT) WHERE MEM_NUM=?";

	@Override
	public void update(MemberProfileVO memberProfileVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(UPDATE);

			Blob blob = con.createBlob();

			con.setAutoCommit(false);

			pstmt.setString(1, memberProfileVO.getMem_name());
			pstmt.setString(2, memberProfileVO.getMem_pwd());
			pstmt.setString(3, memberProfileVO.getMobile());
			pstmt.setString(4, memberProfileVO.getEmail());
			pstmt.setString(5, memberProfileVO.getAddress());

			if (memberProfileVO.getMem_img() != null) {
				blob.setBytes(1, memberProfileVO.getMem_img());
			} else {
				blob = null;
			}

			pstmt.setBlob(6, blob);

			if (memberProfileVO.getRem_point() != null) {
				pstmt.setInt(7, memberProfileVO.getRem_point());
			} else {
				pstmt.setNull(7, java.sql.Types.INTEGER);
			}
			
			pstmt.setString(8, memberProfileVO.getMem_num());

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
	public MemberProfileVO getMemProfile(MemberProfileVO memberProfileVO) {
		MemberProfileVO memVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MEMBERPROFILE);

			pstmt.setString(1, memberProfileVO.getMem_num());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				memVO = new MemberProfileVO();
				memVO.setMem_num(rs.getString("MEM_NUM"));
				memVO.setMem_acc(rs.getString("MEM_ACC"));
				memVO.setMem_pwd(rs.getString("MEM_PWD"));
				memVO.setMem_name(rs.getString("MEM_NAME"));
				memVO.setSex(rs.getString("SEX"));
				memVO.setAge(rs.getInt("AGE"));
				memVO.setMobile(rs.getString("MOBILE"));
				memVO.setCek_mobile(rs.getString("CEK_MOBILE"));
				memVO.setEmail(rs.getString("EMAIL"));
				memVO.setAddress(rs.getString("ADDRESS"));
				memVO.setRem_point(rs.getInt("REM_POINT"));
				// memVO.setMem_pic(rs.getBytes(12));
				// memVO.setStatus(rs.getString("STATUS"));
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
		return memVO;
	}
	
}
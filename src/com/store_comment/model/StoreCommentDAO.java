package com.store_comment.model;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.store_profile.model.StoreProfileVO;

public class StoreCommentDAO implements StoreCommentDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String UPDATE = "UPDATE STORE_COMMENT SET COM_TITLE=NVL(?, COM_TITLE), STARS=NVL(?, STARS), COMMENTT=NVL(?, COMMENTT) WHERE COM_NUM=?";
	private static final String GET_MYCOMMENTS = "SELECT * FROM (SELECT T.*, ROWNUM R FROM (SELECT COM_NUM ,COM_TITLE, STARS, COMMENTT, COM_TIME, STO_NAME, SC.STO_NUM FROM STORE_COMMENT SC FULL OUTER JOIN STORE_PROFILE SP ON SC.STO_NUM = SP.STO_NUM WHERE MEM_NUM=? AND STATUS=? ORDER BY COM_TIME DESC) T ) WHERE R BETWEEN ? AND ?";
	
	@Override
	public void update(StoreCommentVO storeCommentVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			con.setAutoCommit(false);

			pstmt.setString(1, storeCommentVO.getCom_title());
			if (storeCommentVO.getStars() == null) {
				pstmt.setString(2, null);
			} else {
				pstmt.setInt(2, storeCommentVO.getStars());
			}
			pstmt.setString(3, storeCommentVO.getCommentt());
			pstmt.setString(4, storeCommentVO.getCom_num());

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
	public List<List> getMyComment(StoreCommentVO storeCommentVO, int rows) {
		StoreCommentVO stoCommentVO = null;
		StoreProfileVO stoProfileVO = null;
		List<List> commentsList = new ArrayList<List>();
		List<Object> myComment = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MYCOMMENTS);

			pstmt.setString(1, storeCommentVO.getMem_num());
			pstmt.setString(2, storeCommentVO.getStatus());
			pstmt.setInt(3, rows);
			pstmt.setInt(4, (rows + 1));

			rs = pstmt.executeQuery();

			while (rs.next()) {
				stoCommentVO = new StoreCommentVO();
				stoProfileVO = new StoreProfileVO();
				myComment = new ArrayList<Object>();

				stoCommentVO.setCom_num(rs.getString("COM_NUM"));
				stoCommentVO.setCom_title(rs.getString("COM_TITLE"));
				stoCommentVO.setCommentt(rs.getString("COMMENTT"));
				stoCommentVO.setStars(rs.getInt("STARS"));
				stoCommentVO.setCom_time(rs.getTimestamp("COM_TIME"));
				stoProfileVO.setSto_name(rs.getString("STO_NAME"));
				stoProfileVO.setSto_num(rs.getString("STO_NUM"));
				
				myComment.add(stoCommentVO);
				myComment.add(stoProfileVO);
				commentsList.add(myComment);
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
		return commentsList;
	}
	
}
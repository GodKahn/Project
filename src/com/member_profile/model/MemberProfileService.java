package com.member_profile.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import javax.servlet.http.Part;

public class MemberProfileService {

	private MemberProfileDAO_interface dao;

	public MemberProfileService() {
		dao = new MemberProfileDAO();
	}
	
	public MemberProfileVO editeMyProfile(String mem_name, String mem_pwd, String mobile, String email, String address,
			Part mem_pic, String mem_num, Integer rem_point) throws IOException {

		MemberProfileVO memberProfileVO = new MemberProfileVO();
		
		memberProfileVO.setMem_name(mem_name);
		memberProfileVO.setMem_pwd(mem_pwd);
		memberProfileVO.setMobile(mobile);
		memberProfileVO.setEmail(email);
		memberProfileVO.setAddress(address);
		if (mem_pic != null && getFileNameFromPart(mem_pic) != null) {
			memberProfileVO.setMem_img(getPictureByteArray(mem_pic.getInputStream()));
		} else {
			memberProfileVO.setMem_img(null);
		}
		memberProfileVO.setMem_num(mem_num);
		memberProfileVO.setRem_point(rem_point);

		dao.update(memberProfileVO);

		return memberProfileVO;
	}

	public MemberProfileVO getMyProfile(String mem_num) {

		MemberProfileVO memberProfileVO = new MemberProfileVO();

		memberProfileVO.setMem_num(mem_num);

		return dao.getMemProfile(memberProfileVO);
	}

	public static byte[] getPictureByteArray(InputStream in) throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024 * 4];
		int i;
		while ((i = in.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		in.close();

		return baos.toByteArray();
	}

	public String getFileNameFromPart(Part part) {
		String header = part.getHeader("content-disposition");
		String filename = new File(header.substring(header.lastIndexOf("=") + 2, header.length() - 1)).getName();
		if (filename.length() == 0) {
			return null;
		}
		return filename;
	}
	
}

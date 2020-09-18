package cn.smbms.pojo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

@Data
public class Provider {
	
	private Integer id;   //id
//	@Length(min = 3,max = 20,message = "供应商编码长度应该为3~20个字符")
	@NotEmpty(message = "供应商编码不能为空")
	private String proCode; //供应商编码
	private String proName; //供应商名称
	@Length(min = 10,message = "描述不得少于十个字符")
	private String proDesc; //供应商描述
	private String proContact; //供应商联系人
	private String proPhone; //供应商电话
	private String proAddress; //供应商地址
	private String proFax; //供应商传真
	private Integer createdBy; //创建者
	private Date creationDate; //创建时间
	private Integer modifyBy; //更新者
	private Date modifyDate;//更新时间

	
}

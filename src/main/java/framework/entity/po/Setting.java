package framework.entity.po;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 服务配置表
 * 
 * @author sunchangjunn
 *
 */
@Entity
@Table(name = "t_setting")
public class Setting implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7026639370371799829L;
	// 主键id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@Column(name = "id")
	private Long id;
	// 设置标题
	@Column(name = "title")
	private String title;
	// 设置内容
	@Column(name = "code")
	private String code;
	// 创建时间/开始时间
	@Column(name = "ctime")
	private Long ctime;
	// 修改时间/结束时间
	@Column(name = "utime")
	private Long utime;
	// 是否删除(-1:删除;1:正常启用)
	@Column(name = "is_delete")
	private Short isDelete;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getCtime() {
		return ctime;
	}

	public void setCtime(Long ctime) {
		this.ctime = ctime;
	}

	public Long getUtime() {
		return utime;
	}

	public void setUtime(Long utime) {
		this.utime = utime;
	}

	public Short getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Short isDelete) {
		this.isDelete = isDelete;
	}

}

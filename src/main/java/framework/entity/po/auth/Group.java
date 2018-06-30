package framework.entity.po.auth;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "t_auth_group")
public class Group implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@Column(name = "group_id", unique = true, nullable = false)
	private Long groupId;

	@Column(name = "group_name", nullable = false, length = 50)
	private String groupName;

	@Column(name = "remarks", length = 200)
	private String remarks;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "group")
	private List<GroupMenu> groupMenuList;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "group")
	private List<GroupAuthority> groupAuthorityList;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "group")
	private List<GroupUser> groupUserList;

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<GroupMenu> getGroupMenuList() {
		return groupMenuList;
	}

	public void setGroupMenuList(List<GroupMenu> groupMenuList) {
		this.groupMenuList = groupMenuList;
	}

	public List<GroupAuthority> getGroupAuthorityList() {
		return groupAuthorityList;
	}

	public void setGroupAuthorityList(List<GroupAuthority> groupAuthorityList) {
		this.groupAuthorityList = groupAuthorityList;
	}

	public List<GroupUser> getGroupUserList() {
		return groupUserList;
	}

	public void setGroupUserList(List<GroupUser> groupUserList) {
		this.groupUserList = groupUserList;
	}

}
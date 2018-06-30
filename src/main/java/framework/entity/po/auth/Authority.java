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
@Table(name = "t_auth_authority")
public class Authority implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@Column(name = "authority_id", unique = true, nullable = false)
	private Long authorityId;

	@Column(name = "authority_code", length = 50)
	private String authorityCode;

	@Column(name = "authority_name", length = 50)
	private String authorityName;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "authority")
	private List<GroupAuthority> groupAuthorityList;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "authority")
	private List<ResourceAuthority> resourceAuthorityList;

	public Long getAuthorityId() {
		return authorityId;
	}

	public void setAuthorityId(Long authorityId) {
		this.authorityId = authorityId;
	}

	public String getAuthorityCode() {
		return authorityCode;
	}

	public void setAuthorityCode(String authorityCode) {
		this.authorityCode = authorityCode;
	}

	public String getAuthorityName() {
		return authorityName;
	}

	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}

	public List<GroupAuthority> getGroupAuthorityList() {
		return groupAuthorityList;
	}

	public void setGroupAuthorityList(List<GroupAuthority> groupAuthorityList) {
		this.groupAuthorityList = groupAuthorityList;
	}

	public List<ResourceAuthority> getResourceAuthorityList() {
		return resourceAuthorityList;
	}

	public void setResourceAuthorityList(List<ResourceAuthority> resourceAuthorityList) {
		this.resourceAuthorityList = resourceAuthorityList;
	}

}
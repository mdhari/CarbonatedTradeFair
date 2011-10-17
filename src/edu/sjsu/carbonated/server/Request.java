package edu.sjsu.carbonated.server;


import javax.persistence.*;


@Entity
@Table(name="request")
//@NamedQueries({
//		@NamedQuery(name = "getQPBJobs", query = "SELECT r from carbonatedtrademdb.request r WHERE r.request_type=0")})
		//@NamedQuery(name = "findEntries", query = "SELECT e from LogEntry e WHERE e.parent = :parent") })
public class Request{


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="job_id")
	public int jobId;

	@Column(name="request_body")
	public String requestBody;

	@Column(name="request_status")
	public int requestStatus;

	@Column(name="request_type")
	public int requestType;

    public Request() {
    }

	public int getJobId() {
		return this.jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public String getRequestBody() {
		return this.requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public int getRequestStatus() {
		return this.requestStatus;
	}

	public void setRequestStatus(int requestStatus) {
		this.requestStatus = requestStatus;
	}

	public int getRequestType() {
		return this.requestType;
	}

	public void setRequestType(int requestType) {
		this.requestType = requestType;
	}

	@Override
	public String toString() {
		return "Request [jobId=" + jobId + ", requestBody=" + requestBody
				+ ", requestStatus=" + requestStatus + ", requestType="
				+ requestType + "]";
	}
	
	

}
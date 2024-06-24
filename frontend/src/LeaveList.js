import React, {useEffect, useState} from 'react';
import axios from 'axios';
import './LeaveList.css';
import request from "./utils/request";

const LeaveList = () => {
    const [leaves, setLeaves] = useState([]);

    useEffect(() => {
        fetchLeaves();
    }, []);

    const fetchLeaves = () => {
        try {
            // const response = await axios.get('http://localhost:8080/api/leaves/one');
            request({
                url: '/leaves/list', method: 'GET', params: {}
            }).then(res => {
                if (res.code !== 200) {
                    console.log('error！')
                    return
                }
                setLeaves(res.data)
            })
        } catch (error) {
            console.error('Error fetching leaves:', error);
        }
    };

    const handleReApply = async (leaveId) => {
        try {
            await axios.put(`http://localhost:8080/api/leaves/${leaveId}/reapply`);
            fetchLeaves();
        } catch (error) {
            console.error('Error re-applying leave:', error);
        }
    };

    return (
        <div className="leave-list">
            <h2 className="leave-list-title">Re-Compensation Leave</h2>
            <table className="leave-table">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Employee</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Reason</th>
                    <th>Status</th>
                    <th>Type</th>
                    <th>Comment</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                {leaves.map((leave) => (
                    <tr key={leave.id}>
                        <td>{leave.id}</td>
                        <td>{leave.employee.name}</td>
                        <td>{leave.start}</td>
                        <td>{leave.end}</td>
                        <td>{leave.reasons}</td>
                        <td>
                            <span className={`status-${leave.status}`}>{leave.status}</span>
                        </td>
                        <td>{leave.type}</td>
                        <td>{leave.comment || 'N/A'}</td>
                        <td>
                            <button onClick={() => handleReApply(leave.id)}>ReApply</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default LeaveList;

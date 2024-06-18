import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

const LeaveList = () => {
    const [leaves, setLeaves] = useState([]);

    useEffect(() => {
        fetchLeaves();
    }, []);

    const fetchLeaves = async () => {
        try {
            const response = await axios.get('/manager/leaves');
            setLeaves(response.data);
        } catch (error) {
            console.error('Error fetching leaves:', error);
        }
    };

    const handleApprove = async (id) => {
        try {
            await axios.put(`/api/leaves/${id}/approve`);
            fetchLeaves();
        } catch (error) {
            console.error('Error approving leave:', error);
        }
    };

    const handleReject = async (id) => {
        try {
            await axios.put(`/api/leaves/${id}/reject`);
            fetchLeaves();
        } catch (error) {
            console.error('Error rejecting leave:', error);
        }
    };

    return (
        <div>
            <h2>Applied Leaves</h2>
            <thead>
                    <tr>
                        <th>Employee Name</th>
                        <th>Start Date</th>
                        <th>End Date</th>
                        <th>Reason</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    {leaves.map((leave) => (
                        <tr key={leave.id}>
                            <td>{leave.employeeName}</td>
                            <td>{leave.startDate}</td>
                            <td>{leave.endDate}</td>
                            <td>{leave.status}</td>
                            <td>
                                <button onClick={() => handleApprove(leave.id)}>
                                    Approve
                                </button>
                                <button onClick={() => handleReject(leave.id)}>
                                    Reject
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            <Link to="/leaveapprove/list">Back to Leave Approve List</Link>
        </div>
    );
};

export default LeaveList;

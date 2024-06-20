import React, { useEffect, useState } from 'react';
import axios from 'axios';

const LeaveList = () => {
  const [leaves, setLeaves] = useState([]);

  useEffect(() => {
    const fetchLeaves = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/leaves/employee/43');
        setLeaves(response.data);
      } catch (error) {
        console.error('Error fetching leaves:', error);
      }
    };

    fetchLeaves();
  }, []);

  return (
    <div>
      <h2>Leave List</h2>
      {leaves.map((leaves) => (
        <div key={leaves.id}>
          <h3>Leave ID: {leaves.id}</h3>
          <p>Employee: {leaves.employee.name}</p>
          <p>Start Date: {leaves.start}</p>
          <p>End Date: {leaves.end}</p>
          <p>Reason: {leaves.reasons}</p>
          <p>Status: {leaves.status}</p>
          <p>Type: {leaves.type.type}</p>
          <p>Comment: {leaves.comment || 'N/A'}</p>
          <hr />
        </div>
      ))}
    </div>
  );
};

export default LeaveList;
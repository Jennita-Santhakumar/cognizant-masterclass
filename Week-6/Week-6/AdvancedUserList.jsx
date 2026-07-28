import React, { useContext } from 'react';
import { UserContext } from './UserContext';
import './AdvancedUserList.css';

const AdvancedUserList = () => {
  const { users, loading, error, deleteUser } = useContext(UserContext);

  const handleDelete = (id, name) => {
    if (window.confirm(`Delete ${name}?`)) {
      deleteUser(id);
    }
  };

  if (loading) {
    return (
      <div className="state-container">
        <div className="spinner" />
        <p>Loading users...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="state-container error-state">
        <p>⚠️ {error}</p>
      </div>
    );
  }

  if (!users || users.length === 0) {
    return (
      <div className="state-container empty-state">
        <p>No users found. Add your first user using the form.</p>
      </div>
    );
  }

  return (
    <div className="advanced-list-container">
      <h2>User List</h2>
      <div className="table-wrapper">
        <table className="user-table">
          <thead>
            <tr>
              <th>Name</th>
              <th>Email</th>
              <th>Phone</th>
              <th>Address</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {users.map((user) => (
              <tr key={user.id}>
                <td>{user.name}</td>
                <td>{user.email}</td>
                <td>{user.phone}</td>
                <td>{user.address}</td>
                <td>
                  <button
                    className="btn-delete"
                    onClick={() => handleDelete(user.id, user.name)}
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default AdvancedUserList;

import React, { createContext, useState, useCallback, useEffect } from 'react';

// 1. Create the Context
export const UserContext = createContext(null);

const API_BASE_URL = 'http://localhost:8080/api/users';

export const UserProvider = ({ children }) => {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  // Fetch all users
  const fetchUsers = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const response = await fetch(API_BASE_URL);
      if (!response.ok) {
        throw new Error(`Failed to fetch users: ${response.status}`);
      }
      const data = await response.json();
      setUsers(data);
    } catch (err) {
      setError(err.message || 'Something went wrong while fetching users');
    } finally {
      setLoading(false);
    }
  }, []);

  // Add a new user
  const addUser = useCallback(async (user) => {
    setLoading(true);
    setError(null);
    try {
      const response = await fetch(API_BASE_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(user),
      });
      if (!response.ok) {
        throw new Error(`Failed to add user: ${response.status}`);
      }
      const newUser = await response.json();
      setUsers((prev) => [...prev, newUser]);
      return newUser;
    } catch (err) {
      setError(err.message || 'Something went wrong while adding user');
      throw err;
    } finally {
      setLoading(false);
    }
  }, []);

  // Update an existing user
  const updateUser = useCallback(async (id, updatedFields) => {
    setLoading(true);
    setError(null);
    try {
      const response = await fetch(`${API_BASE_URL}/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(updatedFields),
      });
      if (!response.ok) {
        throw new Error(`Failed to update user: ${response.status}`);
      }
      const updated = await response.json();
      setUsers((prev) => prev.map((u) => (u.id === id ? updated : u)));
      return updated;
    } catch (err) {
      setError(err.message || 'Something went wrong while updating user');
      throw err;
    } finally {
      setLoading(false);
    }
  }, []);

  // Delete a user
  const deleteUser = useCallback(async (id) => {
    setLoading(true);
    setError(null);
    try {
      const response = await fetch(`${API_BASE_URL}/${id}`, {
        method: 'DELETE',
      });
      if (!response.ok) {
        throw new Error(`Failed to delete user: ${response.status}`);
      }
      setUsers((prev) => prev.filter((u) => u.id !== id));
    } catch (err) {
      setError(err.message || 'Something went wrong while deleting user');
      throw err;
    } finally {
      setLoading(false);
    }
  }, []);

  // Fetch users on initial mount
  useEffect(() => {
    fetchUsers();
  }, [fetchUsers]);

  const value = {
    users,
    loading,
    error,
    fetchUsers,
    addUser,
    updateUser,
    deleteUser,
  };

  return <UserContext.Provider value={value}>{children}</UserContext.Provider>;
};

export default UserContext;

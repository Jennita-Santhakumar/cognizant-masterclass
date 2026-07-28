import React, { useContext } from 'react';
import { UserContext } from './UserContext';
import useFormValidation from './useFormValidation';
import './AdvancedUserForm.css';

const initialValues = {
  name: '',
  email: '',
  phone: '',
  address: '',
};

const AdvancedUserForm = () => {
  const { addUser } = useContext(UserContext);

  const onSubmit = async (formValues) => {
    await addUser(formValues);
  };

  const {
    values,
    errors,
    touched,
    isSubmitting,
    handleChange,
    handleBlur,
    handleSubmit,
    resetForm,
  } = useFormValidation(initialValues, onSubmit);

  const fields = [
    { name: 'name', label: 'Full Name', type: 'text', placeholder: 'Enter full name' },
    { name: 'email', label: 'Email', type: 'email', placeholder: 'Enter email address' },
    { name: 'phone', label: 'Phone', type: 'tel', placeholder: '10-digit phone number' },
    { name: 'address', label: 'Address', type: 'text', placeholder: 'Enter address' },
  ];

  return (
    <div className="advanced-form-container">
      <h2>Add New User</h2>
      <form onSubmit={handleSubmit} noValidate>
        {fields.map(({ name, label, type, placeholder }) => (
          <div className="form-group" key={name}>
            <label htmlFor={name}>{label}</label>
            <input
              id={name}
              name={name}
              type={type}
              placeholder={placeholder}
              value={values[name]}
              onChange={handleChange}
              onBlur={handleBlur}
              className={touched[name] && errors[name] ? 'input-error' : ''}
            />
            {touched[name] && errors[name] && (
              <span className="error-message">{errors[name]}</span>
            )}
          </div>
        ))}

        <div className="form-actions">
          <button type="submit" disabled={isSubmitting}>
            {isSubmitting ? 'Saving...' : 'Add User'}
          </button>
          <button type="button" className="btn-secondary" onClick={resetForm} disabled={isSubmitting}>
            Reset
          </button>
        </div>
      </form>
    </div>
  );
};

export default AdvancedUserForm;

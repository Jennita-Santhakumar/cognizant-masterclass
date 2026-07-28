import { useState, useCallback } from 'react';

// Default validation rules matching Week 6 spec
const validators = {
  name: (value) => {
    if (!value || !value.trim()) return 'Name is required';
    if (value.trim().length < 3) return 'Name must be at least 3 characters';
    return '';
  },
  email: (value) => {
    if (!value || !value.trim()) return 'Email is required';
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(value)) return 'Enter a valid email address';
    return '';
  },
  phone: (value) => {
    if (!value || !value.trim()) return 'Phone number is required';
    const phoneRegex = /^\d{10}$/;
    if (!phoneRegex.test(value)) return 'Phone number must be exactly 10 digits';
    return '';
  },
  address: (value) => {
    if (!value || !value.trim()) return 'Address is required';
    if (value.trim().length < 5) return 'Address must be at least 5 characters';
    return '';
  },
};

const validateField = (name, value) => {
  const validator = validators[name];
  return validator ? validator(value) : '';
};

const validateAll = (values) => {
  const errors = {};
  Object.keys(values).forEach((key) => {
    const message = validateField(key, values[key]);
    if (message) errors[key] = message;
  });
  return errors;
};

/**
 * Custom hook for managing form state, validation, and submission.
 * @param {Object} initialValues - initial field values
 * @param {Function} onSubmit - called with values when validation passes
 */
export default function useFormValidation(initialValues, onSubmit) {
  const [values, setValues] = useState(initialValues);
  const [errors, setErrors] = useState({});
  const [touched, setTouched] = useState({});
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleChange = useCallback((e) => {
    const { name, value } = e.target;
    setValues((prev) => ({ ...prev, [name]: value }));

    // Re-validate live if the field was already touched
    setErrors((prev) => {
      if (!touched[name]) return prev;
      const message = validateField(name, value);
      return { ...prev, [name]: message };
    });
  }, [touched]);

  const handleBlur = useCallback((e) => {
    const { name, value } = e.target;
    setTouched((prev) => ({ ...prev, [name]: true }));
    setErrors((prev) => ({ ...prev, [name]: validateField(name, value) }));
  }, []);

  const handleSubmit = useCallback(async (e) => {
    if (e && e.preventDefault) e.preventDefault();

    const allErrors = validateAll(values);
    setErrors(allErrors);
    setTouched(
      Object.keys(values).reduce((acc, key) => ({ ...acc, [key]: true }), {})
    );

    if (Object.keys(allErrors).length > 0) {
      return;
    }

    try {
      setIsSubmitting(true);
      await onSubmit(values);
      resetForm();
    } finally {
      setIsSubmitting(false);
    }
  }, [values, onSubmit]);

  const resetForm = useCallback(() => {
    setValues(initialValues);
    setErrors({});
    setTouched({});
  }, [initialValues]);

  return {
    values,
    errors,
    touched,
    isSubmitting,
    handleChange,
    handleBlur,
    handleSubmit,
    resetForm,
  };
}

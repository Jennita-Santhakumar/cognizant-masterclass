# Week 6 - Advanced React Patterns

**Date**: 23-Jul-26
**Time**: 4:00 PM - 6:00 PM
**Tracks**: Java FSE React + .NET FSE React

## Overview

Week 6 extends Week 5 concepts with **advanced React patterns** including:
- **Context API** for global state management
- **Custom Hooks** for reusable logic
- **Form Validation** patterns
- **Error & Loading States**
- **Component Composition**

## Components

### 1. UserContext.jsx
**Purpose**: Global state management using React Context API

**Key Features**:
- `createContext` for shared state
- `useCallback` optimization
- Async operations (fetch, add, delete, update users)
- Error & loading state management

**Usage**:
```jsx
import { UserProvider } from './UserContext';

function App() {
  return (
    <UserProvider>
      {/* Child components have access to UserContext */}
    </UserProvider>
  );
}
```

### 2. useFormValidation.js
**Purpose**: Custom hook for form state & validation logic

**Key Features**:
- Form state management (values, errors, touched)
- Field validation with custom rules
- Real-time validation on blur
- Submit handling with validation
- Reset functionality

**Validation Rules**:
- `name`: Required, min 3 characters
- `email`: Required, valid email format
- `phone`: Required, 10 digits only
- `address`: Required, min 5 characters

**Usage**:
```jsx
const { values, errors, touched, handleChange, handleBlur, handleSubmit }
  = useFormValidation(initialValues, onSubmit);
```

### 3. AdvancedUserForm.jsx
**Purpose**: Form component with validation using custom hook & Context

**Key Features**:
- Uses `useFormValidation` custom hook
- Consumes `UserContext` for `addUser` action
- Real-time validation feedback
- Error messages displayed on blur
- Disabled state during submission
- Reset button to clear form

**Improvements over Week 5**:
- Form validation before submission
- Touched state for error display timing
- Reusable validation logic
- Better UX with error states

### 4. AdvancedUserList.jsx
**Purpose**: User list component with Context consumption

**Key Features**:
- Consumes `UserContext` (users, loading, error, deleteUser)
- Conditional rendering patterns:
  - Loading state with spinner
  - Error state with message
  - Empty state with message
  - Data state with table
- Delete with confirmation dialog
- Responsive table design

### 5. Week6App.jsx
**Purpose**: Main application component

**Structure**:
- `UserProvider` wrapper for Context
- Two-column layout (Form + List)
- Header with title & description
- Footer with course info

## Concepts Demonstrated

### Context API
```jsx
const { users, loading, error, addUser } = useContext(UserContext);
```
- Eliminates prop drilling
- Provides global state
- Reduces component coupling

### Custom Hooks
```jsx
const { values, errors, handleChange, handleSubmit } = useFormValidation(init, onSubmit);
```
- Encapsulates reusable logic
- Improves code organization
- Makes components simpler & more focused

### Conditional Rendering
```jsx
{loading ? <Spinner /> : error ? <Error /> : users.length === 0 ? <Empty /> : <Table />}
```
- Multiple states (loading, error, empty, data)
- Clear UX for each state
- Better error handling

### Form Validation
- Real-time field validation
- Touched state for error timing
- Regex patterns for email/phone
- Custom validation rules

### Event Handling
```jsx
const handleDelete = (id, name) => {
  if (window.confirm(`Delete ${name}?`)) {
    deleteUser(id);
  }
};
```
- Confirmation dialogs
- Async operations
- Error handling

## File Structure

```
Week-6/
├── UserContext.jsx              # Global state management
├── useFormValidation.js         # Custom validation hook
├── AdvancedUserForm.jsx         # Form with validation
├── AdvancedUserList.jsx         # List with Context
├── Week6App.jsx                 # Main app component
├── AdvancedUserForm.css         # Form styling
├── AdvancedUserList.css         # List styling
├── Week6App.css                 # Main app styling
└── README.md                    # This file
```

## Key Improvements from Week 5

| Aspect | Week 5 | Week 6 |
|--------|--------|--------|
| State Management | Component state | Context API |
| Validation | None | Custom hook with validation |
| Error Handling | Basic error state | Comprehensive (loading, error, empty) |
| Form Logic | Inline in component | Custom hook (reusable) |
| Prop Passing | Direct props | Context (no prop drilling) |
| UX States | Basic | Multiple states with feedback |

## Learning Outcomes

✅ Understand React Context API
✅ Create and use custom hooks
✅ Implement form validation patterns
✅ Handle async operations properly
✅ Manage multiple UI states
✅ Improve code organization & reusability
✅ Better error handling & UX

## Running the App

1. Ensure backend API is running on `http://localhost:8080`
2. Replace main App in your React project with `Week6App.jsx`
3. Import all components and styles
4. Run `npm start`

## API Endpoints Required

```
GET    /api/users              # Fetch all users
POST   /api/users              # Create user
PUT    /api/users/{id}         # Update user
DELETE /api/users/{id}         # Delete user
```

## Push to GitHub

```
git add Week-6
git commit -m "Week 6: Advanced React Patterns"
git push
```

## Next Steps (Week 7)

- DevOps, Docker, Cloud
- GenAI fundamentals
- Deployment patterns

---

**Created**: 28-Jul-26
**Track**: Java FSE + .NET FSE React
**Status**: Ready for GitHub commit

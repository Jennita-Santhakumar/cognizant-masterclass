import React from 'react';
import { UserProvider } from './UserContext';
import AdvancedUserForm from './AdvancedUserForm';
import AdvancedUserList from './AdvancedUserList';
import './Week6App.css';

const Week6App = () => {
  return (
    <UserProvider>
      <div className="week6-app">
        <header className="app-header">
          <h1>Week 6: Advanced React Patterns</h1>
          <p>Context API · Custom Hooks · Form Validation · Error &amp; Loading States</p>
        </header>

        <main className="app-main">
          <section className="form-section">
            <AdvancedUserForm />
          </section>
          <section className="list-section">
            <AdvancedUserList />
          </section>
        </main>

        <footer className="app-footer">
          <p>Cognizant Masterclass · Java FSE React + .NET FSE React · Week 6</p>
        </footer>
      </div>
    </UserProvider>
  );
};

export default Week6App;

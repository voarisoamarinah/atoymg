import React from 'react';
import LoginPage from './pages/LoginPage'; // 1. On importe ta page de connexion

function App() {
  return (
    <div className="App">
      {/* 2. On appelle ton composant ici */}
      <LoginPage /> 
    </div>
  );
}

export default App;
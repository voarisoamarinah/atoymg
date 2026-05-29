import React, { useState } from 'react';

function LoginPage() {
    // 1. Déclaration des States pour stocker les données du formulaire
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    // 2. Fonction qui gère la soumission du formulaire
    const handleSubmit = (event) => {
        event.preventDefault(); // Empêche le rechargement de la page

        // Pour l'instant, on affiche juste les données dans la console
        console.log('Tentative de connexion avec :');
        console.log('Username:', username);
        console.log('Mot de passe:', password);

        // C'est ici qu'on ajoutera plus tard l'appel API (Axios ou Fetch)
    };

    // 3. Structure de la page (JSX)
    return (
        <div className="login-page-container">
            <h1>Connexion</h1>

            <form onSubmit={handleSubmit}>
                {/* Champ Username */}
                <div>
                    <label htmlFor="username-input">Username :</label>
                    <input
                        id="username-input"
                        type="text"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        placeholder="username"
                        required
                    />
                </div>

                {/* Champ Mot de passe */}
                <div>
                    <label htmlFor="password-input">Mot de passe :</label>
                    <input
                        id="password-input"
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        placeholder="Votre mot de passe"
                        required
                    />
                </div>

                {/* Bouton de soumission */}
                <button type="submit">Se connecter</button>
            </form>
        </div>
    );
}

export default LoginPage;
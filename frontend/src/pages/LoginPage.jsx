import React, { useState } from 'react';

function LoginPage() {
    // 1. Déclaration des States pour stocker les données du formulaire
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    // 2. Fonction qui gère la soumission du formulaire
    const handleSubmit = async (event) => {
        event.preventDefault();

        try {
            // 1. Envoi de la vraie requête HTTP POST à ton API
            const response = await fetch('http://localhost:8080/atoymg/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json', // On précise qu'on envoie du JSON
                },
                body: JSON.stringify({
                    username: username,
                    password: password
                }),
            });

            // 2. On extrait les données JSON renvoyées par ton serveur
            const data = await response.json();

            // 3. On vérifie si le serveur a renvoyé un statut d'erreur (ex: 401, 400, 500)
            if (!response.ok) {
                // Si ton API renvoie un message d'erreur spécifique (ex: { message: "..." })
                throw new Error(data.message || 'Une erreur est survenue lors de la connexion.');
            }

            // 4. Si tout est OK (statut 200-299)
            // setSuccessMessage('Connexion réussie !');
            console.log('Données reçues de ton API :', data);

            const token = data.data;

            if (token) {
                // On enregistre le token dans le localStorage sous la clé "atoymg_token"
                localStorage.setItem('atoymg_token', token);

                // C'est ici qu'on pourra déclencher la redirection vers le tableau de bord
                console.log('Le token a bien été stocké localement !');
            } else {
                console.log("Le serveur n'a pas renvoyé de jeton de connexion.");
            }

            // C'est ici qu'on récupérera ton Token (ex: data.token) pour le stocker

        } catch (error) {
            console.log('Erreur :', error.message);
        }
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
# Projet_IA

<h1>Prérequis</h1>
<p>JAVA</p>
<p>Donner les droits d'exécution sur le fichier .jar</p>

<h1>Démarrage</h1>
<p>ouvrir le .jar</p>



<h1>Utilisation</h1>
    <h2>Les menus</h2>
        <p>Le menu fichier propose:<p>
        <p>Ouvrir un nouveau Systeme</p>
        <p>Charger un système depuis un fichier xml</p>
        <p>Enregistrer le systeme en cours d'édition</p>
        <p>Enregistrer le système en cours d'édition si il s'agit de la première sauvegarde</p>
        <p>Fermer l'application. Demande la sauvegarde, si pas encore effectuée.</p>
        
        <p>Le menu Mode propose:</p>
        <p>Chaînage avant profondeur</p>
        <p>Chaînage avant largeur</p>
        <p>chaînage arrière</p>
        
        <p>Le menu Préférences propose:</p>
        <p>Des règlages pour l'apparence de l'interface graphique</p>
        
    <h2>Utilisation des éléments de base. Panneau latéral droite</h2>
        <h3>Ajout d'une catégorie</h3>
            <p>Utiliser l'élément 'Catégorie' pour ajouter une catégorie</p>
            <p>Une catégorie est présente en un seul exemplaire dans la base</p>
        <h3>Ajout d'un Fait</h3>
            <p>Utilser l'élément 'Fait' pour ajouter un nouveau Fait</p>
            <p>Les faits peuvent être positifs ou négatifs. Exemple: Fait1 ou !Fait2</p>
            <p>La négation d'un fait est représentée par !</p>
            <p>Un fait est sa négation ne peuvent être contenus à la fois dans la base de Faits</p>
        <h3>Ajout d'une règle</h3>
            <p>Utiliser l'élément Règle pour ajouter un règle</p>
            <p>Une règle est associée à une catégorie. Il faut donc choisir une catégorie dans la liste prévue
            pour pouvoir ajouter une règle.</p>
            <p>Il faut donc au moins une catégorie existante pour ajouter une règle dans la base.</p>
            <p>Pour indiquer plusieurs prémisses et/ou plusieurs conséquences, il faut les écrire une par ligne dans
            les zones prévues à cette effet.</p>
            
            
    <h2>Utilisation du panneau central</h2>
        <p>Dans ce panneau on retrouve des onglets pour :</p>
            <p>L'affichage de la base de Catégories.</p>
            <p>L'affichage de la base de Faits.</p>
            <p>L'affichage de la base de Règles.</p>
            <p>L'affichage des Résultats.</p>
            
            
    <h2>Utilisation des éléments de Test Système Expert</h2>
        <p>Pour tester un plusieurs buts à la fois, il faut saisir la liste des buts à tester
            dans la zone de texte située en bas à gauche.</p>
        <p>Pour tester plusieurs buts à la fois, écrire :   goal1 & !goal2 & !goal3 & goal4 etc...</p>
        <p>On peut choisir une et une seule catégorie de règles à utiliser, ou toutes ensembles</p>
        <p>Tester ave le bouton TESTER</p>
        
        
    <h2>Lecture des résultats</h2>
        <p>Pour chaque test, sont affcihés :</p>
        <p>Les buts à atteindre</p>
        <p>La catégorie de règles à utiliser</p>
        <p>Le mode de fonctionnement de l'algorithme</p>
        <p>La liste des termes demandables</p>
        <p>La liste des faits de la base de Faits</p>
        <p>L'historique des règles utilisées</p>
        <p>Le résultat du test</p>
        
    <h2>Remarques</h2>
        <p>En cas de succès du test, le ou les faits sont ajoutés à la base de Faits.(Sauf si déja présent(s))</p>
        <p>Dans le cas du châinage arrière, il n'y a pas d'historique.</p>
        <p>Dans le cas du chaînage arrière, une liste de faits à ajouter dans la base est proposée à l'utilisateur 
            lorsque le but ne peut pas être atteint</p>
    
  

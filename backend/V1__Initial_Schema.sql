-- Active: 1745323907898@@127.0.0.1@5432@bibliotheque
-- ============================================================
--  SCHÉMA B2B ÉVÉNEMENTIEL — SaaS + Marketplace
--  PostgreSQL — 100% relationnel, sans ENUM, sans paiement
--  Convention : camelCase, FK préfixées par "ID"
--
--  Deux parcours supportés :
--    (A) Client via organisateur (B2B) : client.IDorganizer renseigné
--    (B) Client en direct avec un prestataire : client.IDorganizer NULL
-- ============================================================
CREATE DATABASE atoydotmg;
\c atoydotmg;
-- ------------------------------------------------------------
-- TABLES DE RÉFÉRENCE
-- ------------------------------------------------------------

CREATE TABLE eventTypes (
    id    SERIAL PRIMARY KEY,
    code  VARCHAR(50)  NOT NULL UNIQUE,
    label VARCHAR(100) NOT NULL
);

CREATE TABLE eventStatuses (
    id    SERIAL PRIMARY KEY,
    code  VARCHAR(50)  NOT NULL UNIQUE,
    label VARCHAR(100) NOT NULL
);

CREATE TABLE vendorCategories (
    id    SERIAL PRIMARY KEY,
    code  VARCHAR(50)  NOT NULL UNIQUE,
    label VARCHAR(100) NOT NULL
);

-- ------------------------------------------------------------
-- Tags liés à une catégorie de prestataire
-- ex: catégorie "Traiteur" → tags "Halal", "Végétarien", "Buffet"
-- ------------------------------------------------------------
CREATE TABLE vendorTags (
    id                  SERIAL PRIMARY KEY,
    IDvendorCategory   INT          NOT NULL REFERENCES vendorCategories(id) ON DELETE CASCADE,
    label               VARCHAR(100) NOT NULL,
    CONSTRAINT uq_vendorTag UNIQUE (IDvendorCategory, label)
);

CREATE TABLE eventVendorStatuses (
    id    SERIAL PRIMARY KEY,
    code  VARCHAR(50)  NOT NULL UNIQUE,
    label VARCHAR(100) NOT NULL
);

-- ------------------------------------------------------------
-- ORGANISATEURS (Clients B2B / Agences)
-- ------------------------------------------------------------

CREATE TABLE organizers (
    id          SERIAL PRIMARY KEY,
    agencyName  VARCHAR(255) NOT NULL,
    slug        VARCHAR(255) NOT NULL UNIQUE,
    email       VARCHAR(255) NOT NULL UNIQUE,
    phone       VARCHAR(30),
    city        VARCHAR(2),
    isActive    BOOLEAN      NOT NULL DEFAULT TRUE,
    createdAt   TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updatedAt   TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

-- ------------------------------------------------------------
-- CLIENTS FINAUX
-- ------------------------------------------------------------

CREATE TABLE clients (
    id              SERIAL PRIMARY KEY,
    fullName        VARCHAR(255) NOT NULL,
    email           VARCHAR(255),
    phone           VARCHAR(30),
    notes           TEXT,
    createdAt       TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

-- ------------------------------------------------------------
-- ÉVÉNEMENTS
-- ------------------------------------------------------------

CREATE TABLE events (
    id                  SERIAL PRIMARY KEY,
    -- NULL si l'événement est géré directement par le client (parcours direct)
    IDorganizer        INT            REFERENCES organizers(id) ON DELETE SET NULL,
    IDclient           INT            NOT NULL REFERENCES clients(id),
    IDeventType        INT            NOT NULL REFERENCES eventTypes(id),
    IDeventStatus      INT            NOT NULL REFERENCES eventStatuses(id),
    title               VARCHAR(255)   NOT NULL,
    eventDate           DATE           NOT NULL,
    endDate             DATE,
    venueCity           VARCHAR(100),
    guestCount          INT,
    totalBudget         NUMERIC(12,2),
    notes               TEXT,
    createdAt           TIMESTAMPTZ    NOT NULL DEFAULT NOW(),
    updatedAt           TIMESTAMPTZ    NOT NULL DEFAULT NOW(),

    CONSTRAINT chk_dates       CHECK (endDate IS NULL OR endDate >= eventDate),
    CONSTRAINT chk_totalBudget CHECK (totalBudget IS NULL OR totalBudget >= 0),
    CONSTRAINT chk_guestCount  CHECK (guestCount IS NULL OR guestCount > 0)
);

-- ------------------------------------------------------------
-- PRESTATAIRES (Marketplace / Annuaire)
-- ------------------------------------------------------------

CREATE TABLE vendors (
    id                  SERIAL PRIMARY KEY,
    IDvendorCategory   INT            NOT NULL REFERENCES vendorCategories(id),
    businessName        VARCHAR(255)   NOT NULL,
    contactEmail        VARCHAR(255),
    contactPhone        VARCHAR(30),
    website             VARCHAR(500),
    city                VARCHAR(100),
    countryCode         VARCHAR(2),
    basePrice           NUMERIC(12,2),
    rating              NUMERIC(2,1)   CHECK (rating BETWEEN 1 AND 5),
    ratingCount         INT            NOT NULL DEFAULT 0,
    description         TEXT,
    isVerified          BOOLEAN        NOT NULL DEFAULT FALSE,
    isActive            BOOLEAN        NOT NULL DEFAULT TRUE,
    createdAt           TIMESTAMPTZ    NOT NULL DEFAULT NOW(),
    updatedAt           TIMESTAMPTZ    NOT NULL DEFAULT NOW()
);

-- Liaison prestataire ↔ tags (un prestataire peut avoir plusieurs tags)
CREATE TABLE vendorTagLinks (
    id          SERIAL PRIMARY KEY,
    IDvendor   INT NOT NULL REFERENCES vendors(id) ON DELETE CASCADE,
    IDtag      INT NOT NULL REFERENCES vendorTags(id) ON DELETE CASCADE,
    CONSTRAINT uq_vendorTagLink UNIQUE (IDvendor, IDtag)
);

-- ------------------------------------------------------------
-- LIAISON ÉVÉNEMENT ↔ PRESTATAIRE
-- ------------------------------------------------------------

CREATE TABLE eventVendors (
    id                      SERIAL PRIMARY KEY,
    IDevent                INT            NOT NULL REFERENCES events(id) ON DELETE CASCADE,
    IDvendor               INT            NOT NULL REFERENCES vendors(id),
    IDeventVendorStatus    INT            NOT NULL REFERENCES eventVendorStatuses(id),
    agreedPrice             NUMERIC(12,2),
    notes                   TEXT,
    createdAt               TIMESTAMPTZ    NOT NULL DEFAULT NOW(),
    updatedAt               TIMESTAMPTZ    NOT NULL DEFAULT NOW(),

    CONSTRAINT uq_eventVendor  UNIQUE (IDevent, IDvendor),
    CONSTRAINT chk_agreedPrice CHECK (agreedPrice IS NULL OR agreedPrice >= 0)
);

-- ------------------------------------------------------------
-- DOCUMENTS (contrats, devis liés à un eventVendor)
-- ------------------------------------------------------------

CREATE TABLE documents (
    id              SERIAL PRIMARY KEY,
    IDeventVendor  INT            NOT NULL REFERENCES eventVendors(id) ON DELETE CASCADE,
    documentType    VARCHAR(50)    NOT NULL,  -- 'devis', 'contrat', 'bon_de_commande'
    fileUrl         VARCHAR(1000)  NOT NULL,
    createdAt       TIMESTAMPTZ    NOT NULL DEFAULT NOW()
);

-- ------------------------------------------------------------
-- AVIS PRESTATAIRES (post-événement)
-- ------------------------------------------------------------

CREATE TABLE vendorReviews (
    id              SERIAL PRIMARY KEY,
    IDvendor       INT            NOT NULL REFERENCES vendors(id) ON DELETE CASCADE,
    IDevent        INT            NOT NULL REFERENCES events(id) ON DELETE CASCADE,
    -- NULL si l'avis vient d'un client direct (sans agence)
    IDorganizer    INT            REFERENCES organizers(id) ON DELETE SET NULL,
    IDclient       INT            REFERENCES clients(id) ON DELETE SET NULL,
    score           SMALLINT       NOT NULL CHECK (score BETWEEN 1 AND 5),
    comment         TEXT,
    createdAt       TIMESTAMPTZ    NOT NULL DEFAULT NOW(),

    CONSTRAINT uq_vendorReview   UNIQUE (IDvendor, IDevent),
    -- L'avis doit être rattaché à au moins un auteur (agence ou client direct)
    CONSTRAINT chk_reviewAuthor  CHECK (IDorganizer IS NOT NULL OR IDclient IS NOT NULL)
);

-- ------------------------------------------------------------
-- INDEX
-- ------------------------------------------------------------

CREATE INDEX idx_events_organizer       ON events(IDorganizer);
CREATE INDEX idx_events_status          ON events(IDeventStatus);
CREATE INDEX idx_events_date            ON events(eventDate);
CREATE INDEX idx_eventVendors_event     ON eventVendors(IDevent);
CREATE INDEX idx_eventVendors_vendor    ON eventVendors(IDvendor);
CREATE INDEX idx_vendors_category       ON vendors(IDvendorCategory);
CREATE INDEX idx_vendors_city           ON vendors(city);
CREATE INDEX idx_vendorTags_category    ON vendorTags(IDvendorCategory);

-- ------------------------------------------------------------
-- TRIGGER : updatedAt automatique
-- ------------------------------------------------------------

CREATE OR REPLACE FUNCTION setUpdatedAt()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updatedAt = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_organizers_updatedAt
    BEFORE UPDATE ON organizers
    FOR EACH ROW EXECUTE FUNCTION setUpdatedAt();

CREATE TRIGGER trg_events_updatedAt
    BEFORE UPDATE ON events
    FOR EACH ROW EXECUTE FUNCTION setUpdatedAt();

CREATE TRIGGER trg_vendors_updatedAt
    BEFORE UPDATE ON vendors
    FOR EACH ROW EXECUTE FUNCTION setUpdatedAt();

CREATE TRIGGER trg_eventVendors_updatedAt
    BEFORE UPDATE ON eventVendors
    FOR EACH ROW EXECUTE FUNCTION setUpdatedAt();

-- ------------------------------------------------------------
-- DONNÉES DE RÉFÉRENCE (seed)
-- ------------------------------------------------------------

INSERT INTO eventTypes (code, label) VALUES
    ('mariage',      'Mariage'),
    ('bapteme',      'Baptême'),
    ('anniversaire', 'Anniversaire'),
    ('seminaire',    'Séminaire'),
    ('soiree_gala',  'Soirée Gala'),
    ('autre',        'Autre');

INSERT INTO eventStatuses (code, label) VALUES
    ('brouillon',  'Brouillon'),
    ('en_cours',   'En cours'),
    ('termine',    'Terminé'),
    ('archive',    'Archivé'),
    ('annule',     'Annulé');

INSERT INTO vendorCategories (code, label) VALUES
    ('lieu',        'Lieu / Salle'),
    ('traiteur',    'Traiteur'),
    ('dj',          'DJ / Animation'),
    ('photographe', 'Photographe'),
    ('videaste',    'Vidéaste'),
    ('fleuriste',   'Fleuriste'),
    ('decoration',  'Décoration'),
    ('transport',   'Transport'),
    ('autre',       'Autre');

-- Tags rattachés à leur catégorie
INSERT INTO vendorTags (IDvendorCategory, label) VALUES
    -- Lieu
    ((SELECT id FROM vendorCategories WHERE code = 'lieu'), 'Outdoor'),
    ((SELECT id FROM vendorCategories WHERE code = 'lieu'), 'Intérieur'),
    ((SELECT id FROM vendorCategories WHERE code = 'lieu'), 'Piscine'),
    ((SELECT id FROM vendorCategories WHERE code = 'lieu'), 'Parking inclus'),
    -- Traiteur
    ((SELECT id FROM vendorCategories WHERE code = 'traiteur'), 'Halal'),
    ((SELECT id FROM vendorCategories WHERE code = 'traiteur'), 'Casher'),
    ((SELECT id FROM vendorCategories WHERE code = 'traiteur'), 'Végétarien'),
    ((SELECT id FROM vendorCategories WHERE code = 'traiteur'), 'Buffet'),
    ((SELECT id FROM vendorCategories WHERE code = 'traiteur'), 'Service à table'),
    -- DJ
    ((SELECT id FROM vendorCategories WHERE code = 'dj'), 'Sono incluse'),
    ((SELECT id FROM vendorCategories WHERE code = 'dj'), 'Lumières incluses'),
    ((SELECT id FROM vendorCategories WHERE code = 'dj'), 'Animation enfants'),
    -- Photographe
    ((SELECT id FROM vendorCategories WHERE code = 'photographe'), 'Album inclus'),
    ((SELECT id FROM vendorCategories WHERE code = 'photographe'), 'Drone'),
    ((SELECT id FROM vendorCategories WHERE code = 'photographe'), 'Photo booth');

INSERT INTO eventVendorStatuses (code, label) VALUES
    ('suggere',        'Suggéré'),
    ('en_negociation', 'En négociation'),
    ('valide_client',  'Validé par le client'),
    ('contrat_signe',  'Contrat signé'),
    ('annule',         'Annulé');
--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.0
-- Dumped by pg_dump version 9.4.0
-- Started on 2016-05-09 04:36:11 ALMT

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 193 (class 3079 OID 12123)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2390 (class 0 OID 0)
-- Dependencies: 193
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 191 (class 1259 OID 87193)
-- Name: apartment_complex; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE apartment_complex (
    id bigint NOT NULL,
    company character varying(255),
    image_url character varying(255),
    lat real,
    location character varying(255),
    lon real,
    name character varying(255),
    region_id bigint
);


ALTER TABLE apartment_complex OWNER TO postgres;

--
-- TOC entry 192 (class 1259 OID 87199)
-- Name: apartment_complex_krisha; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE apartment_complex_krisha (
    id bigint NOT NULL,
    key character varying(255),
    name character varying(255),
    complex_id bigint,
    region_id bigint
);


ALTER TABLE apartment_complex_krisha OWNER TO postgres;

--
-- TOC entry 189 (class 1259 OID 87189)
-- Name: apartment_complex_krisha_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE apartment_complex_krisha_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE apartment_complex_krisha_seq OWNER TO postgres;

--
-- TOC entry 190 (class 1259 OID 87191)
-- Name: apartment_complex_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE apartment_complex_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE apartment_complex_seq OWNER TO postgres;

--
-- TOC entry 172 (class 1259 OID 86867)
-- Name: crawler; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE crawler (
    id bigint NOT NULL,
    created_date timestamp without time zone,
    modified_date timestamp without time zone,
    modifier_name character varying(255),
    alias character varying(50) NOT NULL,
    dest_queue_name character varying(25) NOT NULL,
    last_source_scanned_time timestamp without time zone,
    last_usage timestamp without time zone,
    name character varying(255) NOT NULL,
    run_every character varying(20) NOT NULL,
    status character varying(25) NOT NULL,
    crawlergroup_id bigint
);


ALTER TABLE crawler OWNER TO postgres;

--
-- TOC entry 173 (class 1259 OID 86875)
-- Name: crawler_groups; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE crawler_groups (
    id bigint NOT NULL,
    created_date timestamp without time zone,
    modified_date timestamp without time zone,
    modifier_name character varying(255),
    alias character varying(50) NOT NULL,
    description character varying(500),
    name character varying(255) NOT NULL,
    source_system_base_url character varying(255),
    source_system_type character varying(255),
    status character varying(255),
    use_proxy_servers boolean NOT NULL,
    use_user_agents boolean NOT NULL
);


ALTER TABLE crawler_groups OWNER TO postgres;

--
-- TOC entry 177 (class 1259 OID 86917)
-- Name: crawler_groups_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE crawler_groups_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE crawler_groups_seq OWNER TO postgres;

--
-- TOC entry 174 (class 1259 OID 86883)
-- Name: crawler_parameters; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE crawler_parameters (
    id bigint NOT NULL,
    created_date timestamp without time zone,
    modified_date timestamp without time zone,
    modifier_name character varying(255),
    description character varying(500),
    key character varying(50) NOT NULL,
    name character varying(255) NOT NULL,
    status character varying(255) NOT NULL,
    value character varying(255),
    crawler_id bigint
);


ALTER TABLE crawler_parameters OWNER TO postgres;

--
-- TOC entry 178 (class 1259 OID 86919)
-- Name: crawler_parameters_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE crawler_parameters_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE crawler_parameters_seq OWNER TO postgres;

--
-- TOC entry 175 (class 1259 OID 86891)
-- Name: crawler_proxies; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE crawler_proxies (
    id bigint NOT NULL,
    created_date timestamp without time zone,
    modified_date timestamp without time zone,
    modifier_name character varying(255),
    auth_type character varying(50) NOT NULL,
    description character varying(500),
    host character varying(500) NOT NULL,
    inactive_from timestamp without time zone,
    last_check timestamp without time zone,
    last_usage timestamp without time zone,
    login character varying(50),
    name character varying(255) NOT NULL,
    password character varying(50),
    port integer NOT NULL,
    status character varying(20) NOT NULL,
    usage_count bigint,
    type character varying(20) NOT NULL
);


ALTER TABLE crawler_proxies OWNER TO postgres;

--
-- TOC entry 179 (class 1259 OID 86921)
-- Name: crawler_proxies_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE crawler_proxies_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE crawler_proxies_seq OWNER TO postgres;

--
-- TOC entry 180 (class 1259 OID 86923)
-- Name: crawler_user_agent_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE crawler_user_agent_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE crawler_user_agent_seq OWNER TO postgres;

--
-- TOC entry 176 (class 1259 OID 86899)
-- Name: crawler_user_agents; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE crawler_user_agents (
    id bigint NOT NULL,
    created_date timestamp without time zone,
    modified_date timestamp without time zone,
    modifier_name character varying(255),
    description character varying(500),
    name character varying(255) NOT NULL,
    status character varying(20) NOT NULL,
    user_agent character varying(500) NOT NULL,
    type character varying(20) NOT NULL
);


ALTER TABLE crawler_user_agents OWNER TO postgres;

--
-- TOC entry 181 (class 1259 OID 86925)
-- Name: crawlers_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE crawlers_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE crawlers_seq OWNER TO postgres;

--
-- TOC entry 184 (class 1259 OID 87069)
-- Name: region; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE region (
    id bigint NOT NULL,
    latitude real,
    longitude real,
    name character varying(255),
    parent_id bigint,
    alias character varying(255),
    leftkey character varying(255),
    level character varying(255),
    rightkey character varying(255),
    type character varying(255),
    zoom character varying(255)
);


ALTER TABLE region OWNER TO postgres;

--
-- TOC entry 186 (class 1259 OID 87095)
-- Name: region_irr; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE region_irr (
    id bigint NOT NULL,
    key character varying(255),
    name character varying(255),
    regionid_id bigint
);


ALTER TABLE region_irr OWNER TO postgres;

--
-- TOC entry 187 (class 1259 OID 87101)
-- Name: region_kn; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE region_kn (
    id bigint NOT NULL,
    key character varying(255),
    name character varying(255),
    region_id bigint
);


ALTER TABLE region_kn OWNER TO postgres;

--
-- TOC entry 185 (class 1259 OID 87075)
-- Name: region_krisha; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE region_krisha (
    id bigint NOT NULL,
    name character varying(255),
    region_id bigint,
    key bigint,
    alias character varying(255),
    latitude real,
    leftkey character varying(255),
    level character varying(255),
    longitude real,
    rightkey character varying(255),
    type character varying(255),
    zoom character varying(255)
);


ALTER TABLE region_krisha OWNER TO postgres;

--
-- TOC entry 182 (class 1259 OID 87065)
-- Name: region_krisha_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE region_krisha_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE region_krisha_seq OWNER TO postgres;

--
-- TOC entry 188 (class 1259 OID 87107)
-- Name: region_olx; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE region_olx (
    id bigint NOT NULL,
    key character varying(255),
    name character varying(255),
    type character varying(255),
    regionid_id bigint
);


ALTER TABLE region_olx OWNER TO postgres;

--
-- TOC entry 183 (class 1259 OID 87067)
-- Name: region_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE region_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE region_seq OWNER TO postgres;

--
-- TOC entry 2381 (class 0 OID 87193)
-- Dependencies: 191
-- Data for Name: apartment_complex; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20529, 'АВС Investments Holding', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Moskva_2_55c9dc660236f.jpg', 51.1649017, ' Валиханова, 5', 71.4401016, 'Москва', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20530, 'АССМ-01', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Munar-_1_.jpg', 51.1584015, ' Кудайбердыулы, 17', 71.4955978, 'Мунар', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20531, 'АССМ-01', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/na-glavnuyu.jpg', 51.1593018, ' Манаса/Кудайбердыулы', 71.492897, 'Мунар-2', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20532, 'Mabex Trade', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Naur_z.png', 51.1227989, ' Байтурсынова', 71.4850006, 'Наурыз', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20533, 'ВЭН', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Nezavisimost_3.jpg', 51.1419983, ' Момышулы, 27', 71.4826965, 'Независимость', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20534, 'ЖСК Толенды', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Nipi_3.jpg', 51.1365013, ' Момышулы/Жайдарман', 71.469902, 'Нипи', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20535, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Nov_i_risunok_55700b49a180e.png', 51.1492004, ' Куйши Дины/Жумабаева', 71.4798965, 'Новосел', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20536, 'Orda Invest', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/178_53feba78716ba.jpg', 51.1292, ' Кошкарбаева', 71.4955978, 'Нур Аспан', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20537, 'Султан', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Nur_Otau_3_55c9e1c9e94dd.jpg', 51.1414986, ' Мирзояна, 25', 71.4748001, 'Нур Отау', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20538, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Nur_ai_1.jpg', 51.1696014, ' Жубанова/Абая', 71.4654007, 'Нурай', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20539, 'Оберег', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Obereg-_1_.jpg', 51.1411018, ' Тулебаева', 71.5084991, 'Оберег', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20540, 'Тамыз Инвест Групп', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Omir_4.jpg', 51.1563988, ' Махтумкули/Рыскулбекова', 71.4957962, 'Омир', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20541, 'Континент Ink', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Omir-Ozen.jpg', 51.1142006, ' Жумабаева/Калдаякова', 71.4977036, 'Омир-Озен', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20542, 'Тамыз Инвест Групп', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Orbita_21_55b9b18e36920.jpg', 51.1487007, ' Петрова, 10', 71.4623032, 'Орбита', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20543, 'Тамыз Инвест Групп', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Orbita-2-_4_1_55b9b2491dbc2.jpg', 51.1487007, ' Петрова, 10/1', 71.4617004, 'Орбита-2', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20544, 'Astana Building', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Ordabas_-_1_.jpg', 51.1132011, ' Байтурсынова', 71.5139008, 'Ордабасы', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20545, 'Mainstreet', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Oriental-_3_.jpg', 51.1240005, ' Байтурсынова', 71.4764023, 'Ориенталь', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20546, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/OTAU.jpg', 51.1535988, ' Мусрепова, 7', 71.5011978, 'Отау', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20547, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/05_5559ad3c215b4.jpg', 51.1423988, ' Шарль де Голля, 5', 71.4497986, 'Парижский Квартал', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20548, 'Стройинвест-СК', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_po_Imanova.png', 51.1629982, ' Иманова, 28', 71.4371033, 'по Иманова', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20549, 'Азат-НТ Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Po_Mahtumkuli_2.jpg', 51.1581993, ' Махтумкули', 71.486702, 'по Махтумкули', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20550, 'Шар-Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/po_Mirzoiana_1.jpg', 51.1423988, ' Мирзояна, 23, 23/1, 23/2', 71.4733963, 'по Мирзояна', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20551, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/1_557556b7ce483.png', 51.1607018, ' Кудайбердыулы', 71.4894028, 'по Ш.Кудайбердыулы', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20552, 'Альянс Строй Инвест', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_po_IAnushkevicha_1_511df7d495c0d.jpg', 51.158699, ' Янушкевича, 1', 71.4608002, 'по Янушкевича', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20553, 'АльБаракт Компани', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Praga.png', 51.1279984, ' Интернациональный п.', 71.5876007, 'Прага', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20554, 'Паритет-2003', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Rahat.jpg', 51.1641998, ' Иманова, 26', 71.4445038, 'Рахат', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20555, 'Азбука Жилья', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-_adov_e-kvartal_-_2_.jpg', 51.1194, ' Нажмеденова/Байтурсынова', 71.4746017, 'Садовые Кварталы', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20556, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Eskiznik21__2_.jpg', 51.1609993, ' Махтумкули/Рыскулбекова', 71.5002975, 'Саламат', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20557, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_altanat_2.jpg', 51.1535988, ' Ташенова', 71.4464035, 'Салтанат', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20558, 'Норд-Диалог', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_po_12_magistrali3_511496b01beb9.jpg', 51.1304016, ' Жумабаева/Момышулы', 71.4983978, 'по 12 магистрали/Жумабаева', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20559, 'Акмола Курылыс Материалдары', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK__att__2.jpg', 51.1685982, ' Отырар, 10', 71.4377975, 'Сатты', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20560, 'Тулпар-НТ', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK__aulet.jpg', 51.1612015, ' Кудайбердыулы/Абылайхана', 71.4772034, 'Саулет', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20561, 'Бахус', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_ever_VS-21.png', 51.1257019, ' Кордай/Кошкарбаева', 71.509697, 'Север VS-2', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20562, 'Континент Строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK__edmoi_kontinent__1.jpg', 51.1650009, ' Кенесары', 71.4343033, 'Седьмой Континент', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20563, 'Astana Trade International', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/sezam_552ddb79b6e371_55bee38daee85.jpg', 51.1669006, ' Кенесары/Иманбаевой', 71.4346008, 'Сезам', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20564, 'Гранит Строй Фирма', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/w6bb5magx3jxoj8clfrt_56c3f675dc6bc.jpg', 51.1235008, ' Байтурсынова', 71.4810028, 'Сенатор', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20565, 'Шар-Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/__kazochn_i_mir_5.jpg', 51.1257019, ' Байтурсынова/Кошкарбаева', 71.4775009, 'Сказочный Мир', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20566, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK__olnechn_i__1.jpg', 51.1731987, ' Торайгырова, 3/1', 71.427002, 'Солнечный', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20567, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK__olnechn_i_gorod_2.jpg', 51.1617012, ' Манаса, 20', 71.4964981, 'Солнечный Город', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35195, NULL, NULL, NULL, NULL, NULL, 'Базис на Достык', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20568, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK__olo_2_55b72160a772d.jpg', 51.1470985, ' 1 мкр-н, 23', 71.4654999, 'Соло', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20569, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/__ocial.jpg', 51.1638985, ' Манаса, 23', 71.4927979, 'Социал', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20570, 'BBK Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/__ocium_1.jpg', 51.1862984, ' Досмухамедулы, 1', 71.4862976, 'Социум', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20571, 'Альянс Строй Инвест', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/__tepnoi_1.jpg', 51.1548996, ' Мустафина, 13/1', 71.5067978, 'Степной', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20572, 'Шейхказинвест', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK__uan.png', 51.1632996, ' Жубанова/Кенесары', 71.4608002, 'Суан', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20573, 'Асем Кала', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-_ulu-_1_.jpg', 51.1172981, ' Нажимеденова/Калдаякова', 71.4751968, 'Сулу', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20574, 'Альянс Строй Инвест', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK___mbat__4_.jpg', 51.1385002, ' Момышулы, 8/1', 71.4753036, 'Сымбат', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20575, 'Каз Монтаж Строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Taraz__3.jpg', 51.1708984, ' Абая/Валиханова', 71.4408035, 'Тараз', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20576, 'Теремок', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/full_5267aa1a926a2.jpg', 51.1409988, ' Мирзояна, 20/1', 71.4769974, 'Теремок', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20577, 'Тамыз Инвест Групп', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Territoriia-koforta-2-_5_.jpg', 51.1629982, ' Асан Кайгы/Иманова', 71.4445038, 'Территория Комфорта-2', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20578, 'Восток ЛТД', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Turan_2.jpg', 51.1591988, ' Тархана/Жубанова', 71.4572983, 'Туран', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20579, 'Алмас', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Turkestan_2.jpg', 51.1250992, ' Кошкарбаева', 71.4832993, 'Туркестан', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20580, 'Жана Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Turs_n_Astana_3_55c9e243b771f.jpg', 51.1264992, ' Кошкарбаева', 71.4914017, 'Турсын Астана', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20581, 'Концерн Нуржас', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Ulan_1.jpg', 51.1561012, ' Кудайбердыулы/Рыскулбекова', 71.4953003, 'Улан', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20582, 'Найза-Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Ul_tau_6.jpg', 51.1674995, ' Республики/Отырар', 71.428299, 'Улытау', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20583, 'Каз Монтаж Строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Favorit-_4_.jpg', 51.1772995, ' Богенбай батыра, 56', 71.4281006, 'Фаворит', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20584, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Fakultet1_55c1a40dbf400.jpg', 51.1520004, ' Жумабаева, 16/1', 71.4824982, 'Факультет', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20585, 'Найза-Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Fiesta_2.jpg', 51.1561012, ' Мустафина/Кудайбердыулы', 71.5078964, 'Фиеста', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20586, 'ОМК-центр', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Fordstroi_3.jpg', 51.1627998, ' Иманова/Гастелло', 71.4477005, 'Форт Строй', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20587, 'Astana Trade International', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/636003b_517a6481e9b47.jpg', 51.1663017, ' Кенесары, 47', 71.430397, 'Фортуна', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20588, 'Корпорация Базис-А', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Gorodskoi_romans_1.jpg', 51.1186981, ' Калдаякова/Нажимеденова', 71.4545975, 'Французский Квартал', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20589, 'Highvill Kazakhstan', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/64cc910271d722ba97267d4b2155986a_56b86d05e3b8a.JPG', 51.1281013, ' Кошкарбаева/Байтурсынова', 71.4626007, 'Хайвил Астана', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20590, 'Аруана', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Han_Tengri_3.jpg', 51.1685982, ' Кенесары/Сембинова', 71.4486008, 'Хан Тенгри', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20591, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_SHan_rak_1.jpg', 51.1613007, ' Иманбаева, 5', 71.4354019, 'Шанырак', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20592, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_SHan_rak_premium_2.jpg', 51.1631012, ' Иманова, 18/1', 71.4381027, 'Шанырак Премиум', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20593, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_SHan_rak2-5.jpg', 51.1619987, ' Агыбай батыра/Кошкарбаева', 71.4378967, 'Шанырак-2', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20594, 'Aluan-AS', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_SHapagat_nur__5_55c9e2ae66856.jpg', 51.1660995, ' Кенесары/Иманбаевой', 71.4354019, 'Шапагат Нуры', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20595, 'АльБаракт Компани', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/4-full_55b8bb0481c51.jpg', 51.1300011, ' Промышленный п.', 71.5326996, 'Шарджа', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20596, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-SHahar-_1_.jpg', 51.1666985, ' Иманбаевой, 51', 71.4335022, 'Шахар', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20597, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_r_s_2.jpg', 51.1594009, ' Рыскулбекова/Кудайбердыулы', 71.4990997, 'Ырыс', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20598, 'Эверест', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Everest_55cab66f5125c.jpg', 51.1752014, ' Култобе', 71.4509964, 'Эверест', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20599, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Egalite_1_55c2d4f2bb6c8.jpg', 51.164299, ' Абая/Янушкевича', 71.4664993, 'Эгалите', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20600, 'Astana Trade International', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Edem_Palas_2.jpg', 51.1677017, ' Кенесары, 65, 65/1', 71.4411011, 'Эдем Палас', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20601, 'Astana Trade International', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Edem-palas-2-_1_.jpg', 51.1666985, ' Кенесары, 52', 71.4408035, 'Эдем Палас-2', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20602, 'АССМ-01', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Elnara_3.JPG', 51.1595993, ' Иманова/Жубанова', 71.4593964, 'Эльнара', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20603, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Etyud_3.jpg', 51.1584015, ' Жансугирулы, 4/1', 71.4833984, 'Этюд', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20604, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/YUgo-Vostok_55c2e33d8ae2b.png', 51.1576996, ' Кудайбердыулы', 71.4971008, 'Юго-Восток', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20605, 'Инкада', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/yujn_i.jpg', 51.1720009, ' Сейфуллина, 54/2', 71.4531021, 'Южный', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20840, 'Азбука Жилья', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Garant_3.jpg', 51.1809998, ' Дукенулы, 33/1', 71.4365997, 'Гарант', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20841, 'Каз Строй Инвест', NULL, 51.174099, ' Богенбай батыра, 24к1, 24к2', 71.4031982, 'Гармония', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20842, 'Global Building Contract', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Grand_Alatau_2.jpg', 51.1623993, ' Желтоксан, 2, 2/1, 2/2, 2/3', 71.4197998, 'Гранд Алатау', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20843, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Gulder_2.jpg', 51.1626015, ' Валиханова/Гумара Караша', 71.4402008, 'Гулдер', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20844, 'АруАна-XXI', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/G5nV7DRnTsU_55f7c01806f90.jpg', 51.1831017, ' 150 лет Абая/Тлендиева', 71.3590012, 'Даулеткерей', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20845, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/1-full_51710418aeb7b_51d65efc9d96c.jpg', 51.1693001, ' Сейфуллина, 9', 71.4052963, 'Джунгарские Ворота', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20846, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Drujba_5.jpg', 51.1801987, ' Московская, 18', 71.401001, 'Дружба', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20847, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Evropeiskii_2.jpg', 51.1720009, ' Сарыарка/Джангильдина', 71.4045029, 'Европейский', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20848, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Eltai_5.jpg', 51.1680984, ' Сейфуллина/Ауэзова', 71.4018021, 'Елтай', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20849, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Eltai-2.jpg', 51.167099, ' Сейфуллина/Кумисбекова', 71.3920975, 'Елтай-2', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20850, 'ASI', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Jagalau_6.jpg', 51.1599007, ' Шевченко, 10', 71.4078979, 'Жагалау', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20851, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Jaidarman_1.jpg', 51.1693993, ' Сейфуллина, 3', 71.401001, 'Жайдарман', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20852, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Jastar_2_4.jpg', 51.1679993, ' Сейфуллина/Сарыарка', 71.4013977, 'Жастар-2', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20853, 'Аруана', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Jenis_4_553a1ed20c05e.jpg', 51.1856003, ' Победы, 67', 71.4077988, 'Женис', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20854, 'Азбука Жилья', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/a2df6f9b801705978537cb2c587165bc-P1180498_55406c81151e1_55cabe5092616.JPG', 51.1939011, ' Тлендиева, 44, в мкр. Коктал-2', 71.3286972, 'Жети Жол', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20855, 'Азбука Жилья', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/a2df6f9b801705978537cb2c587165bc-P1180498_55406c81151e1.JPG', 51.1940002, ' Тлендиева, 46, 48, 50', 71.3267975, 'Жети Жол-2', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20856, 'Азбука Жилья', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/4_55406fa60bb55.jpg', 51.1925011, ' Тлендиева, 44/3, в мкр. Коктал-2', 71.3330002, 'Жети Жол-3', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20857, 'Sadi-групп', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Jol__1.jpg', 51.169899, ' Жамбыла, 8', 71.3947983, 'Жибек Жолы', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20858, 'Бахус', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Zapad_1.jpg', 51.1727982, ' Сарыарка, 31/2', 71.4048004, 'Запад', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20859, 'КазГазАстана-НС', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/zere1.png', 51.1848984, ' Акана Сери', 71.3768997, 'Зере', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20860, 'Астана Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Imperiia.JPG', 51.1646996, ' Бегельдинова, 8', 71.411499, 'Империя', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20861, 'Астана Строй KZ', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Koktal-2-_3_.jpg', 51.1859016, ' Тлендиева', 71.3089981, 'Коктал', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20862, 'Астана Строй KZ', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Koktal-_2_.jpg', 51.1943016, ' Тлендиева', 71.324501, 'Коктал-2', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20863, 'Адалстройсервис НС', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Koktem.jpg', 51.1677017, ' Кумисбекова, 8', 71.3998032, 'Коктем', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20864, 'Хайас', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/hhhhhhhhhhjjjjjjjjjjjjjjjjj_54058d6269252.jpg', 51.1910019, ' Тлендиева', 71.3376007, 'Кутты Мекен', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20865, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Leia_2.jpg', 51.1829987, ' Потанина, 3', 71.4026031, 'Лея', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20866, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_1_50ed46482db13_55cabed92ce20.jpg', 51.1884003, ' Победы, 75/2', 71.4056015, 'Максат', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20867, 'Азбука Жилья', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Mahhabat_2.jpg', 51.1800995, ' Сарыарка, 43', 71.4042969, 'Махаббат', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20868, 'Азбука Жилья', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Mahabat2_2.jpg', 51.1789017, ' Сарыарка, 41', 71.4048004, 'Махаббат-2', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20869, 'Азбука Жилья', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/1_554077c286ce3.jpg', 51.1795998, ' Сарыарка, 41', 71.4045029, 'Махаббат-3', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20870, 'АССМ-01', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Medina-_1_.jpg', 51.1890984, ' Акана Сери', 71.3824997, 'Медина', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20871, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Nov_i_risunok_556ee0502ceb2.png', 51.1665993, ' Кумисбекова/Сейфуллина', 71.3988037, 'Мирный', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20872, 'Аруана', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Monblan-de-Lyuks-_1_.jpg', 51.1623993, ' Кенесары/Сарыарка', 71.4077988, 'Монблан Де Люкс', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20873, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-NazKon_r-_1_.jpg', 51.1660004, ' Набережная р. Ишим', 71.3858032, 'Наз Коныр', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20874, 'Жана Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Okjetpes_2.jpg', 51.1801987, ' Московская/Потанина', 71.405899, 'Окжетпес', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20381, 'Корпорация БАЗИС‑А', 'http://www.kn.kz/uploads/cache/jk_list/jk/photos/images/CAM_8_3__RGB_color-copy.jpg', 51.1213989, ' площадь Независимости', 71.4763031, 'Millennium Park', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20380, 'Elite Market Group', 'http://www.kn.kz/uploads/cache/jk_list/jk/photos/images/0056FINAL_572471a450eaf.jpg', 51.1186981, ' Нажимеденова/Аманжолова', 71.4737015, 'Premier Palace', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20379, 'Корпорация БАЗИС‑А', 'http://www.kn.kz/uploads/cache/jk_list/jk/photos/images/1_55093ef78a9bc.jpg', 51.1123009, ' Тауелсиздик/Калдаякова', 71.4586029, 'England', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20382, 'Корпорация БАЗИС‑А', 'http://www.kn.kz/uploads/cache/jk_list/jk/photos/images/2014_23_06__08_39_41__225_53b387487e0cf.jpg', 51.118, ' Калдаякова', 71.4577026, 'Итальянский Квартал', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20814, 'Бахус', 'http://www.kn.kz/uploads/cache/jk_list/jk/photos/images/noch-2.jpg', 51.1745987, '', 71.422699, 'Радиоцентр', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20815, 'Sana Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/02_SANA_Obshii_vid__s_pr___ar__Arka__24_10_2015.JPG', 51.1619987, ' Кенесары/Сарыарка', 71.4089966, 'Edel', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20816, 'ASI', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Avangard_1.jpg', 51.1687012, ' Сейфуллина, 8', 71.4046021, 'Авангард', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20817, 'МАК-Алматы Гор Строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Aika.png', 51.1790009, ' Победы, 45/3', 71.4073029, 'Айка', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20818, 'Корпорация Айкен', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Aiken.png', 51.1716003, ' 188 улица, 23', 71.3794022, 'Айкен', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20819, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_AK_Bota_2.jpg', 51.1736984, ' Республики, 43а', 71.4240036, 'Ак Бота', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20820, 'Стройкласс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-AK-Otau-_1_.jpg', 51.1810989, ' Сулуколь, 14/1', 71.3488007, 'Ак Отау', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20821, 'Стройкласс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Ak-Otau-2-_1_.jpg', 51.1820984, ' Шугыла', 71.3496017, 'Ак Отау-2', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20822, 'Каражат-НС', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Ak_tilek.jpg', 51.1953011, ' Тлендиева', 71.3238983, 'Ак Тiлек', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20823, 'Строй-Контракт', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Ak_Bulak-2.jpg', 51.1657982, ' Абая, 1', 71.4013977, 'Ак-Булак-2', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20824, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Untitled-2_5445e5fe491a0.jpg', 51.1894989, ' Алтынсарина/Есенберлина', 71.4120026, 'Ак-Жол', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20825, 'АССМ-01', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Akerke-_1_.jpg', 51.1679993, ' Сейфуллина/Кумисбекова', 71.3995972, 'Акерке', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20826, 'АССМ-01', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Akerke-2.png', 51.1688004, ' Сейфуллина/Кумисбекова', 71.3936005, 'Акерке-2', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20827, 'Стройкласс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Alt_n-Bosaga-_1_.jpg', 51.1721001, ' Сарыарка/Джангильдина', 71.4013977, 'Алтын Босага', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20383, 'Альянс Строй Инвест', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/1_5540a0caf3c55.jpg', 51.1376991, ' Момышулы, 4', 71.4713974, '13 магистраль', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20384, 'Mabex Trade', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/5_zvezd.png', 51.1422005, ' Шарль де Голля', 71.4484024, '5 звезд', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20385, 'Альянс Строй Инвест', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-6i-mikroraion-_2__54aa5fa71a3a9.jpg', 51.1469002, ' 6 мкр-н, 2', 71.476799, '6 микрорайон', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20386, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Nov_i_risunok_556d791fb7979.png', 51.1450996, ' Шарль де Голля/Тауелсиздик', 71.4570999, '7Я', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20387, 'Pioneеr Invest', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/AkbulakHils-_4_.jpg', 51.1435013, ' Шарль де Голля/Дарабоз', 71.4540024, 'Akbulak Hills', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20388, 'Шейхказинвест', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Akbulak_haus.png', 51.1371002, ' Акбулак мкр-н, 5', 71.469101, 'Akbulak House', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20389, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/4201511428316123_556fdcf9487b2.jpg', 51.1352005, ' Момышулы/Тауелсиздик', 71.4642029, 'Akbulak Town', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20390, 'АльБаракт Компани', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Alma_Village.png', 51.1735992, ' Шубары п.', 71.6380005, 'Alma Village', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20391, 'КазМунайСтрой KZ', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/90455437_4_1000x700_zhk-crystal-apartments-nedvizhimost_5639cad2c7bac.jpg', 51.118, ' Сауран/Алматы', 71.4181976, 'Crystal Apartments', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20392, 'Номад Строй ЭК', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/full_52368de9a2ff6.jpg', 51.1245995, ' Кошкарбаева', 71.495697, 'Future Home', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20393, 'Highvill Kazakhstan', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/haiv_ishim_55b8b79466621.png', 51.1277008, ' Байтурсынова', 71.4671021, 'Highvill Ishim', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20394, 'Manhattan Residence', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Untitled-2_547e8e2222b63.jpg', 51.1222, ' Жумабаева/Байтурсынова', 71.4872971, 'Manhattan Residence', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20395, 'Гранд Проект Астана', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/2668a7105-1024x723-4593810-orig_55b8b827aac5e.jpg', 51.1352005, ' Момышулы/Дарабоз', 71.4592972, 'Pioneer', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20828, 'Найза-Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Alt_n_Jibek_10.jpg', 51.1609993, ' Кирова/Гумара Караша', 71.4266968, 'Алтын Жиек', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20829, 'Астана Недвижимость', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Alt_n_orda__3.jpg', 51.1624985, ' Бокейхана, 16', 71.4244003, 'Алтын Орда', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20830, 'Самрук Казына', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Al_Arka_1.jpg', 51.1831017, ' Дукенулы', 71.4385986, 'Аль-Арка', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20831, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Arman_6.JPG', 51.1595001, ' Победы, 1', 71.4140015, 'Арман', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20396, 'Тамыз Инвест Групп', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Sun_Siti_3_55c9cb96b434b.jpg', 51.1617012, ' Габдуллина, 11', 71.4321976, 'Sun City', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20397, 'Стройинвест-СК', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Abai__4.jpg', 51.1679993, ' Габдуллина/Отырар', 71.4319992, 'Абай', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20398, 'Астана Строй Инвест', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Abai-_2__55cab50ef2cf3.jpg', 51.1669998, ' Абая, 21', 71.4123993, 'Абай (по Победы/Абая)', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20399, 'Стройинвест-СК', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_2-1.jpg', 51.1687012, ' Валиханова, 12', 71.4403, 'Абай-2', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20400, 'Астана Строй Сервис', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Ab_lai_Han_1.jpg', 51.1235008, ' Мирзояна/Момышулы', 71.4858017, 'Абылай Хан', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20401, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Avrora_1.jpg', 51.1589012, ' Абылайхана, 6/1', 71.4720993, 'Аврора', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20402, 'ASI', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Adel_1.jpg', 51.1431999, ' Мирзояна, 18', 71.4751968, 'Адель', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20403, 'Азат-НТ Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Azat_2.jpg', 51.1488991, ' Куйши Дины, 30, 30/1, 30/2', 71.4835968, 'Азат', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20404, 'Строй Проект', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Aziia-Plyus-_1_.jpg', 51.1180992, ' Нажимеденова', 71.4770966, 'Азия Плюс', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20405, 'Астана Гюнель Арман', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Aigirim_2_55c9cfcc3dc2f.jpg', 51.1407013, ' Мирзояна/Момышулы', 71.475502, 'Айгерим', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20406, 'Проектная компания Айя', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Aiia.jpg', 51.1618004, ' Иманбаевой, 7б', 71.4344025, 'Айя', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20903, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Titanik_1.jpg', 51.1604996, ' Женис, 3', 71.4132996, 'Титаник', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20407, 'Азия', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Ak_Dala_511476528036b.jpg', 51.157299, ' Мустафина, 21', 71.5118027, 'Ак Дала', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20408, 'Прайс Астана Строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Ak_SHan_rak_2.jpg', 51.1330986, ' Момышулы/Тауелсиздик', 71.4601974, 'Ак Шанырак', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20409, 'Строй-Контракт', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Ak_Bulak_1_5121ef99ab3bf.jpg', 51.1458015, ' Токпанова', 71.449501, 'Ак-Булак', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20410, 'Строй-Контракт', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Ak_Bulak-3.jpg', 51.1408005, ' Дарабоз', 71.4518967, 'Ак-Булак-3', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20411, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Akjar.png', 51.1175003, ' Нажимеденова', 71.4814987, 'Акжар', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20412, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Akkord_51873934dbb0b.jpg', 51.1539993, ' Кудайбердыулы, 31', 71.5064011, 'Аккорд', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20413, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Akkord_Plyus_2.jpg', 51.1526985, ' Кудайбердыулы/Мустафина', 71.5077972, 'Аккорд Плюс', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20414, 'Алматы Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Aksu_518782a2ed940.jpg', 51.141201, ' Момышулы, 25', 71.4806976, 'Аксу', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20415, 'Объединение-Сайран', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Altai_3.jpg', 51.1725998, ' Сембинова/Сейфуллина', 71.4542007, 'Алтай', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20416, 'АССМ-01', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Alt_n_bulak.png', 51.1442986, ' Тауелсиздик/Шарль де Голля', 71.4589005, 'Алтын Булак', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20417, 'Жана Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Alt_n_Gasar_4.jpg', 51.1393013, ' Манаса/Момышулы', 71.4672012, 'Алтын Гасыр', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20418, 'Альянс Строй Инвест', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Alt_n_Korgan.jpg', 51.1386986, ' Момышулы/Мирзояна', 71.4784012, 'Алтын Корган', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20419, 'Тамыз Инвест Групп', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Alt_n_raid_1.jpg', 51.1407013, ' Момышулы, 23', 71.4785995, 'Алтын Раид', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20420, 'КазГазАстана-НС', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Alt_n_Uia.png', 51.1178017, ' Нажимеденова', 71.4794006, 'Алтын Уя', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20421, 'Жана Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Al-Farabi_2_55c47d3000294.jpg', 51.1570015, ' Кудайбердыулы', 71.4971008, 'Аль-Фараби', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20422, 'Жана Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Al_Farabi.png', 51.155899, ' Кудайбердыулы/Рыскулбекова', 71.4972992, 'Аль-Фараби-2', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20423, 'Граждан Строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Amangeld__3.jpg', 51.1601982, ' Брусиловского, 2', 71.4553986, 'Амангельды', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20424, 'Корпорация Базис-А', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Angliiskii_kvartal_2_50f8c632df871_55c9d40adc48b.jpg', 51.1156998, ' Калдаякова', 71.4550018, 'Английский Квартал', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20425, 'Эверест', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Anfilada.jpg', 51.1483994, ' Куйши Дины, 26', 71.4800034, 'Анфилада', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20426, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Alato_4.jpg', 51.1477013, ' Майлина/Жумабаева', 71.4822998, 'Апато', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20427, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Tauer_1_55c9d45a849f7.jpg', 51.1473999, ' Майлина, 29', 71.4812012, 'Апато Тауэр', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20428, 'Жылу Курылыс Астана', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Arabi-2.png', 51.158699, ' Тархана', 71.4555969, 'Араби-2', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20429, 'Альянс Строй Инвест', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Arai_4.jpg', 51.1520004, ' Мусрепова/Абылайхана', 71.5009995, 'Арай', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20430, 'Астана Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/aris5_50ac77f2dcdf4_5189d797edd6d_56b9a293a6240.jpg', 51.1445007, ' Толебаева/Момышулы', 71.4973984, 'Арыстан', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20431, 'Жил Строй Групп', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/kkkkkkkkkkkkkkkk_54478671e1c6e.jpg', 51.1276016, ' Кошкарбаева/Кудайбердыулы', 71.4832001, 'Аспан', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20432, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Astana_Meruert_4.jpg', 51.1399994, ' Момышулы, 12', 71.4793015, 'Астана Меруерт', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20433, 'Астана Недвижимость', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/full_515aa0de54312.jpg', 51.1637001, ' Иманова, 7', 71.4326019, 'Астана Сити', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20434, 'Атлант Инжиниринг', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Atameken_4_51132676aceb3.JPG', 51.1515007, ' Ташенова, 8', 71.4339981, 'Атамекен', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20435, 'Diclodi Partners', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Aulie-Ata-_3_.jpg', 51.1132011, ' Каладаякова', 71.5020981, 'Аулие Ата', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20436, 'Аурика', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/ebfe0edfbe66ced775cedb63647ad19b_515aab0d53d14.JPG', 51.1481018, ' Жирентаева/Куйши Дины', 71.4766006, 'Аурика', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20437, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Ashik_Aspan_1.jpg', 51.1430016, ' Майлина, 8', 71.469101, 'Ашык Аспан', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20438, 'Жана Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Baikonur_1_55c9d50f694ba.jpg', 51.1380997, ' Момышулы, 11', 71.4690018, 'Байконур', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20439, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Baikon_s_3_4fcf3054059f3.jpg', 51.1601982, ' Иманбаева/Тархана', 71.4369965, 'Байконыс', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20440, 'Астана Недвижимость', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Po_ulice_Baraeva_1.jpg', 51.1590996, ' Бараева, 21', 71.4368973, 'Бараева', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20441, 'Астанатрансстрой', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_51d6395e4291b_55c9d56b23633.jpg', 51.1315002, ' Айнакол/Жумабаева', 71.4965973, 'Барыс', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20442, 'Астанатрансстрой', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Bar_s-2-_1_.jpg', 51.1321983, ' Айнаколь/Жумабаева', 71.4989014, 'Барыс-2', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20443, 'Бахус', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Bahus-1.jpg', 51.1450005, ' Мирзояна, 16', 71.4688034, 'Бахус-1', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20444, 'Бахус', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Bahus-2__1_.JPG', 51.1456985, ' Мирзояна', 71.4719009, 'Бахус-2', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20445, 'Mabex Trade', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Baian__ulu.png', 51.1276016, ' Кошкарбаева/Сарыколь', 71.4875031, 'Баян Сулу', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20446, 'АстанаДорИндустрия', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Nov_i_risunok_55406555f2749.jpg', 51.1138992, ' Нажимеденова/Калдаякова', 71.4980011, 'Бейбарыс', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20447, 'Жана Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Bereke_1.jpg', 51.1694984, ' Отырар, 18', 71.4456024, 'Береке', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20448, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/1_5559b28f9180f.jpg', 51.1436996, ' Акбулак-2 мкр-н', 71.4494019, 'Британский Квартал', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20449, 'Акмол Тепло Сервис', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/ab07e768f11f_54c08661cb843.jpeg', 51.1208, ' Байтурсынова/Сарыколь', 71.4882965, 'Венера', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20450, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/3201531425445093_556fde902ab63.jpg', 51.1734009, ' Айманова/Сейфуллина', 71.4319, 'Верный', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20451, 'ASI', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Viktoriia_3.jpg', 51.1313019, ' Момышулы, 2, 2а, 2б', 71.4596024, 'Виктория', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20452, 'ASI', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Vostok_1_50bdd50d7290a.jpg', 51.1405983, ' Момышулы, 16', 71.4831009, 'Восток', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20453, 'Бахус', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Nov_i_risunok__3__55768d7e8104c.png', 51.1547012, ' Мустафина, 13а', 71.5082016, 'Восток-1 (Бахус)', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20454, 'Бахус', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Vostok_2.jpg', 51.1540985, ' Мустафина, 13', 71.5091019, 'Восток-2 (Бахус)', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20455, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_1_50ed4491cea8d.jpg', 51.1576004, ' Кудайбердыулы/Куйши Дины', 71.4925003, 'Восточный', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20456, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Vostochn_i_2.JPG', 51.1563988, ' Куйши Дины, 46/3', 71.4944, 'Восточный-2', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20457, 'Жана Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Grand_Astana_5.jpg', 51.1184006, ' Калдаякова, 11', 71.4633026, 'Гранд Астана', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20458, 'Капитал Строй Сервис', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Granitn_i_1.jpg', 51.1623001, ' Иманова/Бейсекбаева', 71.4529037, 'Гранитный', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20459, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Debyut_1.jpg', 51.1442986, ' Мирзояна, 14', 71.472702, 'Дебют', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20460, 'Азбука Жилья', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/19_54d092a030928.jpg', 51.1281013, ' Кошкарбаева/Жумабаева', 71.4969025, 'Династия', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20461, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Dostar_2.jpg', 51.1575012, ' Мустафина/Мусрепова', 71.5085983, 'Достар', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20462, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Dostar-2_1.jpg', 51.157299, ' Мустафина, 21к1', 71.5093002, 'Достар-2', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20463, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Dostar-3_1.jpg', 51.1581993, ' Мустафина, 21', 71.5074005, 'Достар-3', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20464, 'Жана Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Nov_i_risunok_5537665be9d23.png', 51.1418991, ' Мирзояна, 23', 71.4729004, 'Достык', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20465, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Duet_1.jpg', 51.1674004, ' Габдуллина/Отырар', 71.4300003, 'Дуэт', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20466, 'Бахус-Астана', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Evraziia.jpg', 51.1467018, ' Майлина, 23', 71.4787979, 'Евразия', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20467, 'Мирас Строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_3_51874a1956f67.jpg', 51.1693001, ' Сембинова/Абая', 71.4506989, 'Егемен', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20468, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Enlik_4.jpg', 51.1599998, ' Рыскулбекова, 16', 71.5030975, 'Енлик', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20469, 'Номад Строй ЭК', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/557009_5146acf931bd0.jpg', 51.1680984, ' Габдуллина, 3', 71.430397, 'Есиль', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20470, 'Биком Инжиниринг', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Jaina-_5_.jpg', 51.1302986, ' Кошкарбаева/Кордай', 71.5053024, 'Жайна', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20471, 'ВЭН', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Janat_2.jpg', 51.1545982, ' Абылайхана, 39/1', 71.4909973, 'Жанат', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20472, 'Меир-М', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Januia-_1__55c9d61d28281.jpg', 51.1236992, ' Байтурсынова', 71.4797974, 'Жануя', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20473, 'Азбука Жилья', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Jar-jar_2.jpg', 51.1617012, ' Иманова, 41', 71.4577026, 'Жар-Жар', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20474, 'Биком Инжиниринг', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Jark_n_2_55c9d68409b19.jpg', 51.1654015, ' Абая/Пушкина', 71.4728012, 'Жаркын', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20475, 'Кредит Курылыс НС', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Jas_Kairat_1.jpg', 51.1428986, ' Мирзояна, 21', 71.4735031, 'Жас Кайрат', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20476, 'Казстройподряд', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Jas_Kanat_4.jpg', 51.1506996, ' Абылайхана/Мусрепова', 71.4983978, 'Жас Канат', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20477, 'Ремстрой Астана', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_3_51874f7df0a5d.jpg', 51.1608009, ' Кудайбердыулы/Махтумкули', 71.4871979, 'Жасмин', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20478, 'Ремстрой Астана', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/jasmin-1.jpg', 51.1599998, ' Махтумкули', 71.4985962, 'Жасмин-Оазис', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20479, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Jastar_3.jpg', 51.1417999, ' Момышулы, 18', 71.4854965, 'Жастар', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20480, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Jastar_-3_2.jpg', 51.1615982, ' Иманова/Брусиловского', 71.4553986, 'Жастар-3', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20481, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Jastar-4-_1_.jpg', 51.1535988, ' Мустафина/Кудайбердыулы', 71.5077972, 'Жастар-4', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20482, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Jas_l_El_1.jpg', 51.1414986, ' Мирзояна, 20', 71.4763031, 'Жасыл Ел', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20483, 'Жана Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_518751dd70d28.jpg', 51.1464005, ' Жабаева, 12/2', 71.5580978, 'Железнодорожный', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20484, 'Orda Invest', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JETIGEN.png', 51.1167984, ' Обаган/Калдаякова', 71.4730988, 'Жетiген', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20485, 'КазСтрой', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Nov_i_risunok_553a1f12bfa06.jpg', 51.1627998, ' Иманбаевой, 8', 71.435997, 'Жетысу', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20486, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Jiger_2.jpg', 51.1567993, ' Рыскулбекова', 71.4972, 'Жигер', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20487, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Jiger-2-_2_.jpg', 51.1587982, ' Рыскулбекова/Кудайбердыулы', 71.5020981, 'Жигер-2', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20488, 'Ромул', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Zamechateln_i__1.jpg', 51.1729012, ' Карагандинская, 32', 71.4287033, 'Замечательный', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20489, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Zerde_3.jpg', 51.1631012, ' Кудайбердыулы, 4', 71.478302, 'Зерде', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20490, 'КазГазАстана-НС', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/ZERE2.png', 51.1260986, ' Кошкарбаева', 71.4845963, 'Зере-2', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20491, 'Gradum Development & Investment', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Zolotaia-milia-_1_.jpg', 51.1156998, ' Калдаякова', 71.4729004, 'Золотая Миля', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20492, 'Astana Trade International', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Zolotoi__2__55c9d6fea00a7.jpg', 51.1661987, ' Кенесары/Республики', 71.4297028, 'Золотой Дом', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20493, 'Стройкласс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Izobilie_3.jpg', 51.1605988, ' Тархана, 9', 71.4517975, 'Изобилие', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20494, 'Политренд Астана', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Iskander_1.jpg', 51.1292, ' Армандастар, 2к3', 71.5899963, 'Искандер', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20495, 'Астана Строй Сервис', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Kazahstan_3.jpg', 51.1617012, ' Иманова/Жубанова', 71.4601974, 'Казахстан (по Иманова/Жубанова)', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20496, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Kainar_1.jpg', 51.1487999, ' Куйши Дины/Жумабаева', 71.4815979, 'Кайнар', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20497, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/kainar-22.jpg', 51.1484985, ' Куйши Дины, 28/1', 71.4812012, 'Кайнар-2', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20498, 'Astana Trade International', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_1_50ed46079e0c8.jpg', 51.1680984, ' Кенесары, 69', 71.4453964, 'Каминный', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20499, 'Жана Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Kampus.jpg', 51.1604004, ' Иманова/Жубанова', 71.4597015, 'Кампус', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20500, 'Астана Недвижимость', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Kapital_6_55caba4f7cd5f.jpg', 51.1651001, ' Кенесары/Габдуллина', 71.4309006, 'Капитал', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20501, 'Актив', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Karavan.jpg', 51.1388016, ' Момышулы/Мирзояна', 71.4766006, 'Караван', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20502, 'Актив', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Karavan-2_2.jpg', 51.1386986, ' Момышулы/Мирзояна', 71.4774017, 'Караван-2', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20503, 'Каражат-НС', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/74456413_1_644x461_pomeschenie-svobodnogo-naznacheniya-v-zhk-karazhat-astana_543ba02c097f6.jpg', 51.1553993, ' Абылайхана', 71.4850006, 'Каражат', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20504, 'Акмола Курылыс Материалдары', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Kaharman_5.jpg', 51.1404991, ' Момышулы, 14', 71.4813995, 'Кахарман', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20505, 'Kegoc', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Kegok.jpg', 51.1716003, ' Сейфуллина, 40', 71.4300003, 'Кегок', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20506, 'Альянс Строй Инвест', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Kenesar__4.jpg', 51.1635017, ' Кенесары, 11', 71.4078979, 'Кенесары', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20507, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Nov_i_risunok_556e7af7cda18.png', 51.1562996, ' Мустафина, 15/2', 71.5085983, 'Кереге', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20508, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Kitaiskaia-stena-_1_.jpg', 51.1622009, ' Кенесары/Пушкина', 71.4679031, 'Китайская Стена', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20509, 'Кулагер Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Koksu_1_55c9d78298678.jpg', 51.1636009, ' Валиханова/Иманова', 71.4402008, 'Коксу', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20510, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Krasnaia_derevnia__1_.jpg', 51.1514015, ' Мустафина/Кудайбердыулы', 71.5065002, 'Красная Деревня', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20511, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Kukuruza_55c9d807508c5.jpg', 51.1548004, ' Ташенова', 71.4345016, 'Кукуруза', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20512, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Kulan_4_4fd07e07ef4b7.jpg', 51.1324005, ' Кордай, 77', 71.5045013, 'Кулан', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20513, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Kulan_-2_1.jpg', 51.1329994, ' Таскескен/Кордай', 71.504097, 'Кулан-2', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20514, 'АССМ-01', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Kipshak_3.jpg', 51.1632996, ' Кенесары/Жубанова', 71.4599991, 'Кыпшак', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20515, 'Тамыз Инвест Групп', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Magistraln_i_3.jpg', 51.1380997, ' Момышулы, 13', 71.469902, 'Магистральный', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20516, 'Атлант Инжиниринг', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Magistraln_i-3-_1_.jpg', 51.1394997, ' Момышулы', 71.4721985, 'Магистральный-3', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20517, 'Концерн Строймонолит Астана', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Mariia_3_55c9da6f2eaba.jpg', 51.1395988, ' Момышулы/Мирзояна', 71.4777985, 'Мария', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20518, 'Атлант Инжиниринг', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Medeo.jpg', 51.1707993, ' Омарова, 10', 71.4374008, 'Медеу', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20519, 'Альянс Строй Инвест', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Merei-_1__55c9dad96a562.jpg', 51.1475983, ' Куйшы Дины, 22', 71.4768982, 'Мерей', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20520, 'Майна Ltd', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Merei_interkonti_2.jpg', 51.1691017, ' Абая, 45', 71.4296036, 'Мерей Интерконти', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20521, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Mereke_2.jpg', 51.1605988, ' Абылайхана/Пушкина', 71.4742966, 'Мереке', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20522, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Nov_i_risunok_55680c6fd08ff.png', 51.161499, ' Абылайхана/Пушкина', 71.4738007, 'Мереке-2', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20523, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Mirreti_1.jpg', 51.155201, ' Мустафина, 15', 71.5102997, 'Меррити', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20524, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/img_0702_5559afbfa58ef.jpg', 51.1432991, ' Амман/Шарль де Голля', 71.4458008, 'Миланский Квартал', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20525, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Minima__3_55c9dc1c62d2a.jpg', 51.1660995, ' Циолковского/Абая', 71.469101, 'Минима+', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35197, NULL, NULL, NULL, NULL, NULL, 'Базис на Зенкова 33', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20526, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Miras_1.jpg', 51.1533012, ' Куйши дины/Абылайхана', 71.4862976, 'Мирас', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20527, 'Технополисжилстрой', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Molodaia_semia_3.jpg', 51.1674995, ' Жубанова/Абая', 71.4628983, 'Молодая Семья', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20528, 'Технополисжилстрой', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Molodaia_semia-2_3.jpg', 51.1675987, ' Жубанова, 23', 71.4639969, 'Молодая Семья-2', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20832, 'Корпорация Базис-А', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Astana_juld_z__3.jpg', 51.1575012, ' Республики, 3/2', 71.4276962, 'Астана Жулдызы', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20833, 'Ротор', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Bat_r_9.jpg', 51.1763, ' Сарыарка/Богенбай батыра', 71.4067993, 'Батыр', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20834, 'Тэхне-строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Bogenbai_Bat_r__6.jpg', 51.1769981, ' Богенбай батыра/Торайгырова', 71.4262009, 'Богенбай Батыр', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20835, 'АльБаракт Компани', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Bodrum_554047d208a9b.png', 51.1846008, ' Ардагерлер/Тлендиева', 71.3439026, 'Бодрум', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20836, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Bolashak_2.jpg', 51.1599998, ' Сарыарка, 3', 71.4096985, 'Болашак', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20837, 'Бурлингазстрой', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Burlin_4.jpg', 51.1668015, ' Сарыарка/Абая', 71.4080963, 'Бурлин', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20838, 'Бурлингазстрой', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Burlin_1_5436205e04da2.jpg', 51.1663017, ' Сарыарка/Абая', 71.4073029, 'Бурлин-2', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20839, 'Жана Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Vozrojdenie_2.jpg', 51.1688004, ' Кумисбекова/Сейфуллина', 71.3972015, 'Возрождение', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20875, 'ОМК-центр', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Centr_1.jpg', 51.1713982, ' Сейфуллина/Ауэзова', 71.4235001, 'ОМК Центр', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20876, 'Ромул', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Otrada_11_55bb055aa3a39.jpg', 51.1778984, ' Бейбитшилик, 34/1', 71.4194031, 'Отрада', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20877, 'Доступные Квадраты', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Pana-_2_.jpg', 51.1976013, ' Конституции/Карталинская', 71.3869019, 'Пана', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20878, 'Орал-дизайн-стройсервис', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_po_188-oi_ulice_55cabf4c20562.jpg', 51.1716003, ' 188 улица', 71.3839035, 'по 188-ой улице', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20879, 'МАК-Алматы Гор Строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_po_Moskovskoi.png', 51.1799011, ' Московская, 5/1', 71.3899994, 'по Московской', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20880, 'Орал-дизайн-стройсервис', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Po__eifulina_1.jpg', 51.1715012, ' Сейфуллина/Ауэзова', 71.3808975, 'по Сейфуллина', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20881, 'Ромул', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Po_Tlendieva_3.jpg', 51.1949005, ' Тлендиева', 71.3255997, 'по Тлендиева', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20882, 'Граждан Строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Pobeda_1.jpg', 51.1735001, ' Победы, 28', 71.4123993, 'Победа', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20883, 'Корпорация Базис-А', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Prestij_1.jpg', 51.1605988, ' Желтоксан, 1', 71.4166031, 'Престиж', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20884, 'Атлант Инжиниринг', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/r1_544e24815f13d.jpg', 51.1853981, ' Тлендиева', 71.3544006, 'Рух', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20885, 'Объединение-Сайран', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK__airan_1.jpg', 51.1702003, ' Кубрина, 20/1', 71.4029007, 'Сайран', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20886, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_1_50ed4ab081263.jpg', 51.1627998, ' Кенесары/Кумисбекова', 71.3999023, 'Самрук', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20887, 'Граждан Пром Строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/__ana_5.jpg', 51.1599007, ' Сарыарка/Ирченко', 71.4055023, 'Сана', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20888, 'Ромул', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_apa_2007.jpg', 51.1678009, ' Жамбыла, 7', 71.3899994, 'Сапа-2007', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20889, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/__ar_-Arka_1.jpg', 51.1719017, ' Сарыарка/Джангильдина', 71.405899, 'Сарыарка', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20890, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/__ar_bulak_1.jpg', 51.174099, ' Бейсековой', 71.3822021, 'Сарыбулак', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20891, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/__aiahat_1.jpg', 51.2019005, ' Аспара', 71.3867035, 'Саяхат', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20892, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK__vechki_5.jpg', 51.1749001, ' Сарыарка/Богенбай батыра', 71.405098, 'Свечки', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20893, 'Бахус', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_51d63c99598b7.JPG', 51.1822014, ' Московская, 38', 71.418602, 'Север', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20894, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/__imfoniia_61_55bee6cebeff4.jpg', 51.158699, ' Сарыарка, 1а, 1б', 71.4095993, 'Симфония', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20895, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK__kazka.jpg', 51.1685982, ' Сейфуллина/Сарыарка', 71.4032974, 'Сказка', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20896, 'BBK Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/__kif_1.jpg', 51.1721992, ' Победы, 26а', 71.4128036, 'Скиф', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20897, 'Технополисжилстрой', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK__tatus_2.jpg', 51.178299, ' Желтоксан/Богенбай батыра', 71.415802, 'Статус', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20898, 'Каз Джордан Констрашн Компани', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK__tolichn_i_7.jpg', 51.1647987, ' Абая, 8', 71.4044037, 'Столичный', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20899, 'Каз Джордан Констрашн Компани', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/stolichn_i_2_551bb3a4085ab.jpg', 51.1651001, ' Сарыарка/Абая', 71.4067993, 'Столичный-2', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20900, 'Ромул', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Talapkerskii.jpg', 51.1837006, ' Тлендиева, 16/1', 71.3671036, 'Талапкерский', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20901, 'Тамыз Инвест Групп', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Tam_z_5.jpg', 51.1791992, ' Тлендиева, 15/1', 71.3748016, 'Тамыз', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20902, 'Строй Проект', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_2_50ed4c8948460.jpg', 51.1605988, ' Шевченко, 4', 71.4029999, 'Тархан', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35199, NULL, NULL, NULL, NULL, NULL, 'Базис на Чайковского', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20904, 'Азбука Жилья', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Torlet_2.jpg', 51.1697998, ' Алматинская, 10/1, 10/2, 10/3', 71.3914032, 'Торлет', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20905, 'Объединение-Сайран', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Tulpar__Ob_edinenie__airan_.jpg', 51.1703987, ' Кубрина/Кумисбекова', 71.3989029, 'Тулпар (Объединение-Сайран)', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20906, 'Стройкласс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_1_50ed4cbb983fd_55cac026d196c.jpg', 51.1702995, ' Кубрина, 22/1', 71.4042969, 'Успех', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20907, 'Фортуна НС', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Formula_uspeha__1.jpg', 51.1982002, ' Карталинская, 18/1', 71.3858032, 'Формула Успеха', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20908, 'Sadi-групп', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Celinograd_1.jpg', 51.1706009, ' Кумисбекова/Сейфуллина', 71.3963013, 'Целиноград', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20909, 'Sadi-групп', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Celinograd-2.jpg', 51.169899, ' Сейфуллина/Жамбыла', 71.396698, 'Целиноград-2', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20910, 'Sadi-групп', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/p_tselinograd11_551bc9f9dbef6.png', 51.1691017, ' Кумисбекова/188 улица', 71.3977966, 'Целиноград-3', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20911, 'Алатау KZ', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Eksklyuziv-_1_.jpg', 51.1940002, ' Байсеитовой/Суворова/Дулатова', 71.3886032, 'Эксклюзив', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20912, 'ASI', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_6917_56938aa75cbcf.JPG', 51.1617012, ' Кенесары/Кумисбекова', 71.4017029, 'Эмират', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20913, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Etalon_1.jpg', 51.1753998, ' Богенбай батыра', 71.4030991, 'Эталон', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20918, 'Aldar Properties', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Nov_i_risunok_558a22e9e29d1.png', 51.1226006, ' Сыганак/Акмешит', 71.4281006, 'Abu Dhabi Plaza', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20919, 'Mabex Trade', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Arman_De_Luxe.png', 51.1128006, ' Сауран', 71.4177017, 'Arman De Luxe', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20920, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/art_house1_54b77caf08000.jpg', 51.1156006, ' Алматы/Туркестан', 71.4268036, 'Art House', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20921, 'Orda Invest', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/151_53e063eca8e52.jpg', 51.1138, ' Орынбор/Туркестан', 71.4247971, 'Asyl Park', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20922, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Avesta.png', 51.1074982, ' Енбекшилер', 71.435997, 'Avesta', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20923, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/1_54d058ae7e4ae.jpg', 51.1328011, ' Сыганак/Туран', 71.3975983, 'BI City', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20924, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/p0_5559c4b791606.jpg', 51.0999985, ' Кабанбай батыра', 71.4205017, 'BI Town', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20925, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/20140717-img_6626_5559c2bc463ba.jpg', 51.0890999, ' Орынбор', 71.4373016, 'BI-Village (Коттеджный городок)', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20926, 'Caspian Service Kazakhstan', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/caspian_50cda6f99108c.jpg', 51.1166992, ' Енбекшилер, 17', 71.4395981, 'Caspian Palace', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20927, 'Бест–Групп–НС', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Club_House.png', 51.1113014, ' Енбекшилер', 71.4392014, 'Club House', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20928, 'СМУ — КиН Строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Comfort_House.png', 51.1456985, ' Умай ана/Баян сулу', 71.3908005, 'Comfort House', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20929, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Comfort-Tawn-_6_.JPG', 51.0886993, ' Орынбор', 71.4473038, 'Comfort Town', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20930, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/1_5559bfddd00f2.jpg', 51.0863991, ' Орынбор', 71.4410019, 'Deluxe Town', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20931, 'Orda Invest', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/3_51f8bb881d3a6.jpg', 51.1228981, ' Сыганак/Акмешит', 71.4251022, 'Europe Palace', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20932, 'Orda Invest', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Europe-Palas-2-_1_.jpg', 51.1222, ' Сыганак/Акмешит', 71.4248962, 'Europe Palace-2', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20933, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Nov_i_risunok__1__5565476457609.png', 51.0956001, ' квадрат улиц Хусейн Бен Талала, Орынбор, Рыскулова и Кабанбай батыра', 71.4065018, 'Expo Boulevard', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20934, 'Азбука Жилья', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/5_5458655682d6f.jpg', 51.1021004, ' Орынбор', 71.4310989, 'Expo City', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20935, 'Альтаир – СК7', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/10281893_w640_h640_01_4_56246bceb4e88.jpg', 51.1151009, ' Керей, Жанибек хандары/Акмешит', 71.4218979, 'Expo Towers', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20936, 'Азбука Жилья', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/a1a91786a7c1abd142f81f1010a613d4-33__1_.jpg', 51.0992012, ' Орынбор', 71.4307022, 'Expo Town', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20937, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/1_5559bf0bc40a3.jpg', 51.0582008, ' Кабанбай батыра/Орынбор', 71.4215012, 'Family Town', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20938, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/20140909-img_0106_5559bd0d38862.jpg', 51.0602989, ' Кабанбай батыра/Орынбор', 71.4261017, 'Family Village (Коттеджный городок)', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20939, 'Строй Центр Астана', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Castle-House-_2__54aa6a2b4b899.jpg', 51.1458015, ' Коргалжинское шоссе', 71.4044037, 'G-House', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20940, 'G-Park', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/G-Park_Family.png', 51.0992012, ' Енбекшилер', 71.4309006, 'G-Park Family', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20941, 'G-Park', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/G-Park_Premium.png', 51.1147003, ' Сауран/Керей, Жанибек хандары', 71.4180984, 'G-Park Premium', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20942, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/1_1_5559bb8f8254b.jpg', 51.0513992, ' Чайковского, 149', 71.3957977, 'Garden Village (Коттеджный городок)', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20943, 'Парк Констракшн', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Viladj_1.jpg', 51.1425018, ' Жубан ана', 71.3963013, 'Green Village', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20944, 'Highvill Kazakhstan', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/e2e2_53f194a689006.jpg', 51.103199, ' Кабанбай батыра, 45', 71.4016037, 'Highvill Park', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20915, 'Корпорация БАЗИС‑А', 'http://www.kn.kz/uploads/cache/jk_list/jk/photos/images/1_553f5be8d4997.jpg', 51.0973015, ' Хусейн бен Талала/Кабанбай батыра', 71.4075012, 'Expo Plaza', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21021, 'Бахус', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Za_rekoi.png', 51.0955009, ' Орынбор', 71.4434967, 'За Рекой', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20916, 'Корпорация БАЗИС‑А', 'http://www.kn.kz/uploads/cache/jk_list/jk/photos/images/Triuph_Render-Dvor_1-3-2_5636f87c4872e.jpg', 51.1379013, ' Кабанбай батыра, 13', 71.4143982, 'Триумфальный', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20917, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/jk/photos/images/P8_54000b1b26324.jpg', 51.093399, ' Орынбор', 71.4253006, 'Promenade Expo', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20945, 'Club House Building', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-King-House-_3_.jpg', 51.1473007, ' Комсомольский п.', 71.3893967, 'King House', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20946, 'Базис-А', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-London-_1_.jpg', 51.1199989, ' Нажимеденова', 71.4552002, 'London', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20947, 'Mabex Trade', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/image-22-01-15-183a28-1_55ae0a4504e74.jpeg', 51.1402016, ' Домалак Ана', 71.3930969, 'Max Royal', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20948, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Nov_i_risunok_55701499d7d4d.png', 51.0966988, ' квадрат улиц Орынбор, 26 улица, Туркестан и Акниет', 71.4229965, 'Park Avenue', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20949, 'Азбука Жилья', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/park.jpg', 51.0794983, ' Орынбор/31 улица', 71.4255981, 'Park House', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20950, 'Sembol Investment & Development', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Rixsos-Khan-Shatyr-_3__5639c724585ce.jpg', 51.1307983, ' Туран, 37/6б', 71.4029999, 'Rixos Khan Shatyr Residences', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20951, 'Expolist Investment', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/84727561_3_644x461_zhk-royal-expo-apartments-novostroyki_rev001__1_.jpg', 51.0955009, ' Хусейн бен Талал/Акмешит', 71.4154968, 'Royal Expo Apartments', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20952, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/roial_park.jpg', 51.0625, ' Кабанбай батыра/Орынбор', 71.4207001, 'Royal Park', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20953, 'Black Gold Development', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/full__1__54e30c64299cf.jpg', 51.1442986, ' Баян сулу/Кыз-Жибек', 71.3864975, 'Sky City', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20954, 'Orda Invest', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Sultan-Appartamens-_4_.jpg', 51.1105003, ' Орынбор', 71.4329987, 'Sultan Apartments', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20955, 'Mainstreet', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Viktoria-Palas-_4_.jpg', 51.1526985, ' Караоткель мкр-н', 71.3907013, 'Victoria Palace', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20956, 'Mabetex Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK__aranda.png', 51.1020012, ' Керей, Жанибек хандары', 71.4615021, 'VIP-городок Saranda', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20957, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/img_0014_5559b6838fd42.jpg', 51.1223984, ' Сыганак/Туркестан', 71.4308014, 'Viva Plaza', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20958, 'Orda Invest', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/VIVERE_553f5e36cbc92.png', 51.1090012, ' Орынбор/24 улица', 71.4335022, 'Vivere', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20959, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/1_5322d9b71ecd7.jpg', 51.1203995, ' Алматы/Орынбор', 71.4272003, 'Well House', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20960, 'Акмол Тепло Сервис', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/9046930_w640_h2048_bezymyannyj4_54e3049fc32e4.jpg', 51.1545982, ' Коргалжинское шоссе', 71.3621979, 'Абат', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20961, 'Стройинвест-СК', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Avicena_2.jpg', 51.1346016, ' Сарайшык, 9', 71.4300995, 'Авиценна', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20962, 'Стройинвест-СК', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Avicenna-2.png', 51.1137009, ' Орынбор/Керей, Жанебек хандары', 71.4346008, 'Авиценна-2', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20963, 'Стройинвест-СК', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Avicenna-Elit.JPG', 51.1343994, ' Сарайшык, 11', 71.4327011, 'Авиценна-Элит', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20964, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/7061712_w640_h2048_09_544e1b15cb63b.jpg', 51.0919991, ' Жайсан', 71.4816971, 'Адина', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20965, 'Корпорация Айкен', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/2374810_original_55b8b8673e7a5.jpg', 51.1716995, ' Бейсековой', 71.3792038, 'Айкен-2', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20966, 'Азбука Жилья', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Aisanam-_3_.jpg', 51.1144981, ' Орынбор/Керей, Жанибек хандары', 71.4327011, 'Айсанам', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20967, 'Азбука Жилья', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Aisanam-de-lyuks-_1_.jpg', 51.1128006, ' Орынбор/Керей, Жанибек хандары', 71.4319, 'Айсанам Де Люкс', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20968, 'Астана Недвижимость', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Ak_Orda_1.jpg', 51.1439018, ' Кабанбай батыра', 71.4153976, 'Ак Орда', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20969, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Ak__arai_2.jpg', 51.1441994, ' Айганым', 71.4021988, 'Ак Сарай', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20970, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Ak_sarai-2__1.jpg', 51.1444016, ' Айганым', 71.4023972, 'Ак Сарай-2', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20971, 'ПСК Клён', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Akademiia_1.jpg', 51.1469002, ' Коргалжинское шоссе', 71.3808975, 'Академия', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20972, 'Дуние Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Akjaiik_1.jpg', 51.114399, ' Орынбор, 21', 71.4312973, 'Акжайык', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20973, 'Норд', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Alatau_9.jpg', 51.1192017, ' Туркестан/Сыганак', 71.4298019, 'Алатау', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20974, 'Kazakhstan Invest Group-A', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Almat__2.jpg', 51.1267014, ' Достык, 10', 71.4231033, 'Алматы', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20975, 'Orda Invest', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Alt_n-Orna-_1_.jpg', 51.0979004, ' Орынбор', 71.4300003, 'Алтын Арна', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20976, 'Академия Жилья', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/altyn-orda1_551bd1e0b6806.jpg', 50.9748993, ' Косши п.', 71.3531036, 'Алтын Орда (п. Косши)', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20977, 'Шар-Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Alt_n-shar-_4_.jpg', 51.1185989, ' Туран/Керей, Жанибек хандары', 71.3992996, 'Алтын Шар', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20978, 'Строй-Контракт', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Albion_3.jpg', 51.1488991, ' Коргалжинское шоссе, 6', 71.3840027, 'Альбион', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20979, 'Асем Кала', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/zhk-altair-v-astane-kazdom-1_517a1e05a0e5c.jpg', 51.1352997, ' Сарайшык/Акмешит', 71.4316025, 'Альтаир', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20980, 'ЖСК Толенды', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Anisa-1-_1_.jpg', 51.1445999, ' Коргалжинское шоссе', 71.4017029, 'Аниса', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20981, 'ЖСК Толенды', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Anisa-2.jpg', 51.1469994, ' Коргалжинское шоссе', 71.3812027, 'Аниса-2', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20982, 'ЖСК Толенды', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Anisa-3.jpg', 51.1436996, ' Коргалжинское шоссе', 71.3751984, 'Аниса-3', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20983, 'Жылу Курылыс Астана', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Arabi.png', 51.0760994, ' Кабанбай батыра', 71.4310989, 'Араби', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20984, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/arabica-19289182812_51594cf9b81ff.jpg', 51.1459999, ' Жубан ана', 71.4028015, 'Арабика', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20985, 'Нур Астана Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Arai.png', 51.1355019, ' Чубары мкр-н', 71.4329987, 'Арай-2', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20986, 'Нур Астана Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/ZHK-Araj-2_554063415a9eb.jpg', 51.1362991, ' Чубары мкр-н', 71.4315033, 'Арай-3', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20987, 'Эшел', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Arail_m_4.jpg', 51.1324997, ' Сарайшык, 36', 71.4349976, 'Арайлым', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20988, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/1_556fddd76150a.jpg', 51.0980988, ' Туркестан/Хусейн бен Талала', 71.4236984, 'Арман Кала', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20989, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/cam_009.jpg', 51.0938988, ' Хусейн бен Талал', 71.4456024, 'Арнау', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20990, 'Асем Кала', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Asem_kala_1.jpg', 51.1442986, ' Жубан ана, 5', 71.4001999, 'Асем Кала', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20991, 'Orda Invest', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Asem-Tas-_4_.jpg', 51.1165009, ' Керей, Жанибек хандары', 71.4049988, 'Асем Тас', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20992, 'Альянс Строй Инвест', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/newspictur03042014085052_55405aa9a4fb9.jpg', 51.0979996, ' 33 улица/23 улица, южнее п. Заречный', 71.4346008, 'АСИ 33/23', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20993, 'Astana Capital Building Project', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/1-full_5540b45b984b3.jpg', 51.1156006, ' Орынбор, 22', 71.4349976, 'Астана Жулдызы (по Орынбор)', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20994, 'Атриум', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Atrium_518742aca377f.jpg', 51.1170006, ' Керей, Жанибек хандары/Кабанбай батыра', 71.4085999, 'Атриум', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20995, 'Альянс Строй Инвест', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Bavariia_3.jpg', 51.1464996, ' Макатаева, 7/3', 71.4366989, 'Бавария', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20996, 'Black Gold Development', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Bai_Tau.png', 51.1004982, ' Орынбор/26 улица', 71.4309998, 'Бай Тау', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20997, 'Корпорация Цесна', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Versal_1.jpg', 51.132, ' Сарайшык/Орынбор', 71.4377975, 'Версаль', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20998, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Vizit_1.jpg', 51.1249008, ' Сыганак, 15', 71.4225006, 'Визит', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20999, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/zima_-_1.jpg', 51.1147003, ' Кабанбай батыра/Керей, Жанибек хандары', 71.4148026, 'Времена Года', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21000, 'Альянс Строй Инвест', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Gradokompleks__1_.jpg', 51.1166992, ' Сауран', 71.4176025, 'Градокомплекс', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21001, 'Альянс Строй Инвест', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Gradokompleks-2__2_.jpg', 51.1190987, ' Алматы/Сауран', 71.418602, 'Градокомплекс-2', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21002, 'Альянс Строй Инвест', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Nov_i_risunok__3_.png', 51.1175995, ' Сауран', 71.4205017, 'Градокомплекс-3', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21003, 'Альянс Строй Инвест', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Graciia_3.jpg', 51.1160011, ' Керей, Жанибек хандары, 7', 71.4229965, 'Грация', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21004, 'Альянс Строй Инвест', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Elit.jpg', 51.1161003, ' Акмешит, 7/1', 71.4218979, 'Грация Элит', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21005, 'Арка Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Nov_i_risunok.jpg', 51.1152992, ' Керея, Жанибек хандары, 11', 71.4206009, 'Гулистан', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21006, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Daniia.jpg', 51.1473999, ' Коргалжинское шоссе, 23', 71.3693008, 'Дания', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21007, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/11754ec4f269d0e2-dan1_56c6975160c80.jpg', 51.0990982, ' Хусейн бен Талала/Кабанбай батыра', 71.4129028, 'Данияр', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21008, 'ПСК Клен', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Delfin_1.jpg', 51.1428986, ' Туран, 14', 71.4125977, 'Дельфин', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21009, 'ASI', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Diplomat_5.jpg', 51.1334991, ' Сарайшык, 34', 71.4298019, 'Дипломат', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21010, 'Альянс Строй Инвест', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Dom_.jpg', 51.1492004, ' Слободка', 71.4329987, 'ДомЪ', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21011, 'Shar Trevell', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Dudarai-_2_.jpg', 51.1479988, ' Коргалжинское шоссе', 71.401001, 'Дударай', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21012, 'Астанатрансстрой', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Erke.png', 51.144001, ' Баян сулу', 71.3844986, 'Ерке', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21013, 'Лига Полис', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/full_542e1f1d9fca3.jpg', 51.1433983, ' Кыз-Жибек', 71.3862991, 'Еркежан', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21014, 'ASI', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Jagalau-2_6.jpg', 51.1251984, ' Сарыарка/Сыганак', 71.4123993, 'Жагалау-2', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21015, 'ASI', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Jagalau-3_1.jpg', 51.1348, ' Коргалжинское шоссе', 71.3664017, 'Жагалау-3', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21016, 'ASI', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/68454ec56105ee75-2_55406a7d3d272.jpg', 51.1395988, ' Сыганак', 71.3665009, 'Жагалау-4', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21017, 'Тамыз Инвест Групп', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/695018_517a41d899eb6.jpg', 51.1272011, ' Кабанбай батыра/Сыганак', 71.4073029, 'Жана Толкын', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21018, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Jansaia_4.jpg', 51.1295013, ' Кабанбай батыра/Достык', 71.4156036, 'Жансая', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21019, 'Хайас', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Jar_k-_1_.jpg', 51.110199, ' Енбекшилер', 71.4363022, 'Жарык', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21020, 'Astana Trade International', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Jibek_Jol___Astana_Trade_International_3.jpg', 51.135601, ' Кабанбай батыра', 71.414299, 'Жибек Жолы (Astana Trade International)', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35204, NULL, NULL, NULL, NULL, NULL, 'Бакытты омир', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21022, 'Нур Астана Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Zam_-_Zam.png', 51.1254005, ' Туран', 71.4002991, 'Зам-Зам', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21023, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Zelen_i_kvartal.png', 51.1338997, ' Сыганак', 71.3944016, 'Зеленый Квартал', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21024, 'Корпорация Базис-А', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_1_50ed45ddee476.jpg', 51.1501007, ' Туран, 9', 71.4112015, 'Изумруд', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21025, 'Корпорация Базис-А', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/lazurniy_55b840d57143d.jpg', 51.1302986, ' Кабанбай батыра/Тауелсиздик', 71.422699, 'Изумрудный Квартал', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21026, 'Альянс Строй Инвест', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Infiniti_2.jpg', 51.1214981, ' Кабанбай батыра/Сыганак', 71.4145966, 'Инфинити', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21027, 'Альянс Строй Инвест', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Infiniti-2_1.jpg', 51.1195984, ' Кабанбай батыра', 71.4141006, 'Инфинити-2', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21028, 'ЛАД-Строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Ishim_2.jpg', 51.1194992, ' Енбекшилер/Сыганак', 71.4397964, 'Ишим', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21029, 'Азия', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Karatau.png', 51.1124992, ' Керей, Жанибек хандары/Орынбор', 71.4345016, 'Каратау', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21030, 'Байтур', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Kaskad_4.jpg', 51.1455002, ' Кабанбай батыра', 71.4235001, 'Каскад', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21031, 'СтройСервис-СК', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Keremet-_2_.jpg', 51.1450996, ' Акын Сара/Айганым', 71.403801, 'Керемет', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21032, 'TM Engineering Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Kokjiek.png', 51.1411018, ' Комсомольский п.', 71.4092026, 'Кокжиек', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21033, 'Азбука Жилья', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/96649bb825ddabb838f3cd8933a37064-1_543cbf4d53315.jpg', 51.1423988, ' Кыз Жибек/Улпан', 71.3873978, 'Комсомольский', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21034, 'Нур Астана Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Komsomolskij_5540594cd9fd3.png', 51.1407013, ' Кыз Жибек/Домалак Ана', 71.3924026, 'Комсомольский', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21035, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Komfort--_1_.jpg', 51.1473999, ' Калкаман мкр-н', 71.4244995, 'Комфорт', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21036, 'Азбука Жилья', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Komfort-deluxe-_1__55e537478485f.jpg', 51.1469002, ' Калкаман/Космонавтов', 71.4261017, 'Комфорт De Luxe', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21037, 'АССМ-01', NULL, 51.1497002, ' Коргалжынское шоссе, Е125 улица/Е128 улица', 71.3451996, 'Коргалжинский Квартал', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21038, 'Aibyn Construction Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Korkem-_1_.jpg', 51.1158981, ' Алматы/Орынбор', 71.433197, 'Коркем', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21039, 'Стройкласс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/korkemtower3_542e7066b694c_55b7256749959.jpg', 51.1314011, ' Бейсековой/Сыганак', 71.3619995, 'Коркем Tоwer', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21040, 'Aibyn Construction Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Nov_i_risunok_5540758d0926e.png', 51.110199, ' Туркестан', 71.4268036, 'Коркем-2', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21041, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Kraun_plaza_3.jpg', 51.1221008, ' Сыганак, 18', 71.4326019, 'Краун Плаза', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21042, 'Sadi-групп', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Nov_i_risunok_556d741d0f5f3.png', 51.1423988, ' Сарайшык', 71.3807983, 'Кристалл', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21043, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Kulager_5.jpg', 51.1259995, ' Сауран, 2', 71.4243011, 'Кулагер', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21044, 'Строй-Контракт', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Kulsai_3.jpg', 51.1486015, ' Коргалжинское шоссе, 4, 4/1, 4/2', 71.3924026, 'Кульсай', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21045, 'Азбука Жилья', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_K_z_Jibek_1.jpg', 51.1138992, ' Енбекшилер, 13', 71.4384995, 'Кыз Жибек', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21046, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/20140710-img_6025_5559b0ff94d67.jpg', 51.1357994, ' Сарайшык/Кабанбай батыра', 71.4231033, 'Лазурный Квартал', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21047, 'КазГазАстана-НС', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Marsel.png', 51.1105003, ' Керей, Жанибек хандары', 71.4430008, 'Марсель', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21049, 'Шар-Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Meruert_2.jpg', 51.1248016, ' Сыганак, 21/1', 71.4257965, 'Меруерт', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21050, 'Mabex Trade', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_MJK__3_.jpg', 51.145401, ' Жубан ана, 14', 71.4004974, 'МЖК-1', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21051, 'Mabex Trade', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_MJK-2.png', 51.1456985, ' Жубан ана, 9', 71.4012985, 'МЖК-2', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21052, 'ASI', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Ministerskii_55cabaec00729.jpg', 51.1327019, ' Сарыарка/Сыганак', 71.4008026, 'Министерский', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21053, 'ASI', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Mison_7.jpg', 51.1363983, ' Сарайшык, 5/1', 71.427597, 'Мисон', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21054, 'Корпорация Базис-А', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Na_vodno_zelenom_bulvare_3.jpg', 51.1301994, ' Тауелсиздик/Акмешит', 71.4269028, 'на Водно-Зеленом Бульваре', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21055, 'Астана Недвижимость', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Nov_i_mir_3.jpg', 51.1301994, ' Кунаева, 35, 35/1, 35/2', 71.4383011, 'Новый Мир', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21056, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Nomad_2.jpg', 51.1241989, ' Сыганак, 10', 71.4205017, 'Номад', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21057, 'Азия', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Nurkanat_5.jpg', 51.1150017, ' Енбекшилер', 71.4391022, 'Нур Канат', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21058, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/600_1_89773_1_54d09faa9acf0.jpg', 51.1231995, ' Сыганак/130 улица', 71.4236984, 'Нур-Нур', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21059, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Dala_1.jpg', 51.1301003, ' Кабанбай батыра/Достык', 71.4123001, 'Нурлы Дала', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21060, 'Нур Астана Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/1_55405e5b5c13d.jpg', 51.1403008, ' Комсомольский-2 мкр-н, Карашаш Хана', 71.382103, 'Нурлы Уя', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21061, 'Элитстрой', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/nursaia-yuk_1_.jpg', 51.1292992, ' ул. Кунаева', 71.4367981, 'Нурсая', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35207, NULL, NULL, NULL, NULL, NULL, 'Батыр', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21062, 'Бонита Инжиниринг', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Nursaia_Bonita_1.jpg', 51.1271019, ' Достык, 13', 71.4338989, 'Нурсая Бонита', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21063, 'Astana Trade International', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Olim_Palas_3.jpg', 51.1152, ' Туркестан, 8', 71.4280014, 'Олимп Палас', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21064, 'Astana Trade International', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Olimp-palas-2-_1_.jpg', 51.1132011, ' Керей, Жанибек хандары/Туркестан', 71.4274979, 'Олимп Палас-2', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21065, 'Orda Invest', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/3_51f8bd01ddd56.jpg', 51.1383018, ' Сарайшык/Карашаш ана', 71.4020996, 'Оркен De Luxe', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21066, 'Нур Астана Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Or_nbor.png', 51.1054001, ' Орынбор', 71.4317017, 'Орынбор', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21067, 'Акмола Курылыс Материалдары', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Otandastar_9.jpg', 51.1161995, ' Алматы, 13', 71.4309006, 'Отандастар', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21068, 'Астана Недвижимость', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Ot_rar_4.jpg', 51.1501007, ' Кабанбай батыра, 2/7', 71.4281998, 'Отырар', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21069, 'Политренд Астана', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/1049596_original_5322bf8c1e0d2.jpg', 51.1413002, ' Бейсековой/Коргалжинское шоссе', 71.3666992, 'Памир', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21070, 'Mabex Trade', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Parkov_i.png', 51.1355019, ' Арай', 71.4347992, 'Парковый', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21071, 'Номад Строй ЭК', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Po_Or_nbor_1.jpg', 51.1158981, ' Орынбор', 71.4349976, 'по Орынбор', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21072, 'ВЭН', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_po__araish_k.jpg', 51.1375999, ' Сарайшык', 71.4075012, 'по Сарайшык', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21073, 'Строй Жил Инвест', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Premium_1.jpg', 51.1323013, ' Сарайшык, 38', 71.4364014, 'Премиум', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21074, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/1_5559aba477dee.jpg', 51.1417999, ' Кабанбай батыра', 71.4178009, 'Премьера', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21075, 'Астана Недвижимость', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Raduga_5.jpg', 51.1450996, ' Кабанбай батыра, 5/1', 71.4163971, 'Радуга', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21076, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK__armat.jpg', 51.1222992, ' Сыганак/Сауран', 71.4185028, 'Сармат', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21077, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK__armat2-1.jpg', 51.1234016, ' Сыганак/Сауран', 71.4192963, 'Сармат-2', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21078, 'Альянс Строй Инвест', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK__auran.jpg', 51.1208992, ' Сауран', 71.4197998, 'Сауран', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21079, 'Стройкласс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK__vetl_i_4.jpg', 51.1362, ' Сарайшык, 7/1', 71.4285965, 'Светлый', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21080, 'Астана Недвижимость', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK__evernaia_korona_1.jpg', 51.1487007, ' Кабанбай батыра, 2', 71.4255981, 'Северная Корона', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21081, 'Корпорация Базис-А', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK__evernoe_siianie_51_55bee0b100029.jpg', 51.1282005, ' Достык, 5', 71.4222031, 'Северное Сияние', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21082, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/__em_bochek_1.jpg', 51.1271019, ' Сыганак, 9', 71.4104996, 'Семь Бочек', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21083, 'Алатау KZ', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/g5_5322c4b45ee95.jpg', 51.1260986, ' Сыганак/Туран', 71.4011993, 'Семь Палат', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21084, 'Ерасыл Строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-_en_m-_1_.jpg', 51.1459007, ' Коргалжинское шоссе', 71.3802032, 'Сеным', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21085, 'Astana Trade International', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/__oyuz_Apollon3.jpg', 51.1250992, ' Сыганак/Кабанбай батыра', 71.4107971, 'Союз Аполлон', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21086, 'Тамыз Инвест Групп', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Territoriia_komforta_11_55bf2fe0e80b2.jpg', 51.118, ' 19 улица', 71.4296036, 'Территория Комфорта', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21087, 'Корпорация Базис-А', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/-astan_2.jpg', 51.1407013, ' Кабанбай батыра, 11', 71.4149017, 'Триумф Астаны', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21088, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/p1kor.jpg', 51.1043015, ' Орынбор/25 улица', 71.4301987, 'Триумфальная Арка', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21089, 'Альянс Строй Инвест', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Tulpar_1.jpg', 51.1262016, ' Достык, 12', 71.4260025, 'Тулпар', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21090, 'Альтаир – СК7', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Tulpar-Elit-_1_.jpg', 51.1092987, ' Орынбор', 71.4309006, 'Тулпар Элит', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21091, 'Азбука Жилья', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/7c79c709c3e0dc401458ed760a8412dc-0065_543cebfe1cc53.jpg', 51.1170006, ' Туркестан/Алматы', 71.428299, 'Тумар', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21092, 'Сункар 777', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Turinskii_kvartal.png', 51.0849991, ' Орынбор/29 улица', 71.4733963, 'Туринский Квартал', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21093, 'ASI', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Uyut_4.jpg', 51.1213989, ' Туркестан, 18/1', 71.4309998, 'Уют', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21094, 'Шейхказинвест', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Hazret.png', 51.1478004, ' Коргалжинское шоссе', 71.3444977, 'Хазрет', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21095, 'Найза-Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_CMT_1.jpg', 51.1283989, ' Достык, 1', 71.4177017, 'ЦМТ - Центр Международной Торговли', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21096, 'ПСК Клен', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/1409579335_1406180949_cheremushki_551a66bd911b9.jpg', 51.1088982, ' Туркестан/24 улица', 71.4272003, 'Черемушки', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21097, 'ASI', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-SH_g_s.jpg', 51.1263008, ' Сыганак/Туран', 71.399498, 'Шыгыс', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21098, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Edem_1.jpg', 51.1441994, ' Туран, 19/1', 71.408699, 'Эдем', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21099, 'ЖСК Нур Нур', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_EkoDom.png', 51.1277008, ' Сыганак', 71.3622971, 'Экодом', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21100, 'Астанатрансстрой', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/1-full_56c18ac68c443.jpg', 51.1428986, ' Кургальжинское шоссе', 71.389801, 'Энергетик', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21101, 'Orda Invest', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-IAntarn_i-_1_.jpg', 51.1108017, ' Орынбор/24 улица', 71.4309998, 'Янтарный', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (20914, 'Биком Инжиниринг', 'http://www.kn.kz/uploads/cache/jk_list/jk/photos/images/01-weiss_548e97144e415.png', 51.1302986, ' Сыганак', 71.3712997, 'Citylake', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21109, 'МГТ Астана', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Alt_n_Orda__4_.jpg', 49.8105011, ' Ерубаева, 44/2', 73.0927963, 'Алтын Орда', 239);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21048, 'Элитстрой-2', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/1-full_55b8b99755564.jpg', 51.1212006, NULL, 71.3971024, 'Мейрим', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21102, 'НурайСтрой', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/u138_550bde654bf4a.jpg', 50.9748001, ' Косши п.', 71.3535004, 'Академия (п. Косши)', 731);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21103, 'АльБаракт Компани', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Jailau.png', 50.979599, ' Косши п.', 71.3683014, 'Жайлау', 731);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21104, 'АльБаракт Компани', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Kur_ltai.png', 50.9662018, ' Косши п., 5б мкр-н', 71.3389969, 'Курылтай', 731);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21105, 'Альянс Строй Инвест', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Lesnaia_poliana_1.jpg', 51.0004005, ' Косши п.', 71.3733978, 'Лесная Поляна', 731);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21106, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Orion_1.jpg', 50.9952011, ' Косши п.', 71.3640976, 'Орион', 731);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21110, 'Стройказкомплект', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/__2_.jpg', 49.8069, ' Ерубаева', 73.0935974, 'Альянс', 239);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21111, 'Евразия Стройинвест', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/__1_.jpg', 49.8160019, ' Театральная', 73.0920029, 'Дуэт', 239);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21108, 'Континент-строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_1__2_.jpg', 49.8218002, ' Ленина, 61/2', 73.1052017, 'Diamond', 239);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21107, 'Караганды Бизнес', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/4180b_54f57a152a72e.jpg', 49.793499, ' Ержанова, 18/6', 73.0854034, 'Arman De Luxe', 239);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21112, 'Евразия Стройинвест', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/__3__50fe3f3d5c6e5.jpg', 49.8036995, ' Мичурина, 23', 73.0718002, 'Оазис', 239);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21113, 'Евразия Стройинвест', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/__1__1__55d4004763983.jpg', 49.8073006, ' Бухар-Жырау', 73.0832977, 'Олимп', 239);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21114, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/1_building_Pioner_Alihanov24-5___Kopirovat__55d4017e76c5b.jpg', 49.8079987, ' Алиханова, 24/5', 73.0978012, 'по Алиханова', 239);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21115, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/1_building_Erzhanova-18__Kopirovat__55d401f536a59.jpg', 49.7937012, ' Ержанова, 18', 73.0842972, 'по Ержанова', 239);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21116, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/1_building_Erzhanova-Botsad__Kopirovat_.jpg', 49.7929001, ' Ержанова/Ботанический сад', 73.0759964, 'по Ержанова/Ботанический сад', 239);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21117, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/1_building_Ermekov21-1__Kopirovat_.jpg', 49.7983017, ' Ермекова, 21/1', 73.0940018, 'по Ермекова', 239);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21118, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/2_building_Alyans_Erubaev50-5___Kopirovat__542283aa5ee90.jpg', 49.8069, ' Ерубаева, 50/5', 73.0935974, 'по Ерубаева', 239);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21119, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/1_building_Kostenko-Lenina__Kopirovat__542136ce899c7.jpg', 49.8147011, ' Костенко/Ленина', 73.082901, 'по Костенко/Ленина', 239);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21120, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/DSC_0006_building_Lobody_27-2__Kopirovat__5423a452b293d_55d3ffed6cfd4.jpg', 49.8112984, ' Лободы, 27/2', 73.0963974, 'по Лободы', 239);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21121, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/DSC_0408_building_Lobody_13-3__Kopirovat_.jpg', 49.8100014, ' Лободы, 13/3', 73.0888977, 'по Лободы', 239);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21122, 'Строительная компания Караганды', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_po_ul_Lobod___6_.jpg', 49.8115005, ' Лободы, 25/2', 73.0959015, 'по Лободы', 239);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21123, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/1_building_Tolepov7-2__Kopirovat__55d3ff8abc232.jpg', 49.8135986, ' Толепова, 7/2', 73.1072006, 'по Толепова', 239);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21124, 'Стройказкомплект', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/__5_.jpg', 49.8064995, ' Ерубаева', 73.0945969, 'Фаворит', 239);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21125, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/1_building_Besoba__Kopirovat_.jpg', 49.7957993, ' Шапагат мкр-н', 73.1289978, 'Шапагат', 239);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21126, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/1_building_Yubileynyi__Kopirovat__5422929eb0734_55d3ff39f36b0.jpg', 49.8074989, ' Н. Абдирова', 73.1083984, 'Юбилейный', 239);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21133, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/8ozer4_5184e837875e9.jpg', 43.5116997, ' Каменное плато п.', 77.233902, 'Palm Valley (Коттеджный городок)', 3);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21134, 'Импульс СМ', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/ak-alem-1.jpg', 43.2449989, ' Желтоксан/Курмангазы', 76.9405975, 'Ак Алем', 3);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21135, 'Астана Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/akkz_50fbd9c6cde9a.JPG', 43.2512016, ' Чуланова, 123, 127, 143, 145, 147', 76.8261032, 'Акбулак', 3);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21136, 'Ahsel Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/67267975_resizedScaled_600to450_50ff8481e57fd.jpg', 43.2438011, ' Райымбека/Яссауи', 76.8149033, 'АкКент (ЖК Ахселькент)', 3);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21137, 'Hold Trading Company', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/12373_2algabas.jpg', 43.2401009, ' Алгабас мкр-н', 76.8038025, 'Алгабас (Коттеджный городок)', 3);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21138, 'Астана Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_mkr__Algabas.png', 43.2779007, ' Алгабас мкр-н', 76.8191986, 'мкр. Алгабас', 3);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21139, '', NULL, 43.2551003, NULL, 76.9125977, 'Персия', 3);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21140, 'Корпорация Базис-А', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/1111111111111_55c88a2513046.jpg', 43.2438011, ' Шакарима/Брусиловского', 76.8769989, '29 квартал', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21141, 'APS Construction', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_1727_Stitch.jpg', 43.2473984, ' Нурмакова/Кабанбай батыра', 76.9057999, 'Акмарал', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21142, 'Дом Строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_1689_Stitch.jpg', 43.2448006, ' Жамбыла/Гагарина', 76.8957977, 'Алма-Ата', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21143, 'Global Building Contract', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_29_kv_20_kv_55c88c376ca4d.jpg', 43.2452011, ' Брусиловского, 159', 76.8761978, 'Алтын Булак-1', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21144, 'Рамс Казахстан', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/rams_kabanbay.jpg', 43.2476997, ' Муратбаева/Кабанбай батыра', 76.9189987, 'Ата', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21145, 'Корпорация Базис-А', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Bazis_na_chaikovskogo.jpg', 43.2425003, ' Чайковского', 76.9392014, 'Базис по Чайковского', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21146, 'EastCapitalLimited', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/bereke__almata22.JPG', 43.2499008, ' Богенбай батыра', 76.895401, 'Береке', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21147, 'Kablan', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Vesnovka.png', 43.2471008, ' Кабанбай батыра/Муканова', 76.917099, 'Весновка', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21148, 'Евразия Строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Evraziia.png', 43.2596016, ' Масанчи, 23в', 76.9268036, 'Евразия', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21149, 'Global Building Contract', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Jagalau__46_kvartal_.jpg', 43.2384987, ' Варламова', 76.8722992, 'Жагалау', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21150, 'Серт', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/96fcf10d_resizedScaled_600to450_50ff9e8ccde34.jpg', 43.2490997, ' Толе би/Тлендиева', 76.8667984, 'Жайлы', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21151, 'Рамс Казахстан', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Jasmin.png', 43.2458992, ' Шевченко/Досмухамедова', 76.9244003, 'Жасмин', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21152, 'MAG Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_1877_Stitch.jpg', 43.2438011, ' Курмангазы/Муканова', 76.9175034, 'Жастар', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21153, 'Атамекен', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2143_Stitch_55c88db4bfcbe.jpg', 43.2463989, ' Сейфуллина/Шевченко', 76.9346008, 'Жастар Атамекен', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21154, 'Кенер Инвест Строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_1894_500f707d9486d.jpg', 43.2430992, ' Курмангазы', 76.9181976, 'Изумрудный Город', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21155, 'Global Building Contract', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_2_kvartal_55c88e1a60ed0.jpg', 43.2476997, ' Толе би/Тлендиева', 76.8692017, 'Каусар', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21156, 'Рамс Казахстан', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Komfort_Plyus.png', 43.2435989, ' Сейфуллина/Курмангазы', 76.9354019, 'Комфорт Плюс', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21157, 'Куат', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2215_500e8455e1d5a_55c88e7ad4eaa.jpg', 43.2594986, ' Панфилова/Гоголя', 76.9440994, 'Куат по Панфилова/Гоголя', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21158, 'Куат', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2188_518a2b63249ad.jpg', 43.2563019, ' Фурманова/Казыбек би', 76.9459, 'Куат по Фурманова/Казыбек би', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21159, '', NULL, 43.2481003, ' Толе би/Тлендиева', 76.8705978, 'Ласточкино Гнездо', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21160, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_1649_50f96b640b34e.jpg', 43.2433014, ' Брусиловского/Шакарима', 76.8769989, 'Мега Сайран', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21161, 'ВЕК', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/baiganin_50fc1de66bbb9.jpg', 43.2464981, ' Байганина/Жамбыла', 76.9110031, 'на Байганина', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21162, 'Нико', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/__33_iz_59_.jpg', 43.2477989, ' Жамбыла/Чайковского', 76.9384003, 'Нико по Джамбула/Чайковского', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21163, 'Матай Групп', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2119_Stitch.jpg', 43.2462997, ' Сейфуллина/Жамбыла', 76.9332962, 'Ностальжи', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21164, 'Астана Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_po_Granovskogo.png', 43.2499008, ' Грановского', 76.8683014, 'по Грановского', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21165, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/222222222222222_55c9675fdc00a.jpg', 43.2467995, ' Жамбыла, 105', 76.9261017, 'по Жамбыла', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21166, 'Корпорация Базис-А', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2159_Stitch.jpg', 43.2490005, ' Кабанбай батыра, 122', 76.9400024, 'по Кабанбай батыра', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21167, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_1744_Stitch.jpg', 43.2565002, ' Исаева', 76.9072037, 'по Нурмакова', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21168, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/11111111111111111_55c88f0fac234.jpg', 43.2453003, ' Фурманова', 76.9476013, 'по Фурманова', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21169, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_1787_Stitch.jpg', 43.2471008, ' Шагабутдинова', 76.9209976, 'по Шагабутдинова', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21170, '', NULL, 43.2430992, ' Шевченко/Ауэзова', 76.9029999, 'по Шевченко/Ауэзова', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21171, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/4524_55cc3b6caad30.png', 43.2495995, ' Карасай батыра, 64а', 76.9321976, 'Прогресс', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21172, 'Айнер Сити', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/4524_55cc5c9ece74a.png', 43.2480011, ' Кабанбай батыра/Кожамкулова', 76.9162979, 'Раушан', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21173, 'Корпорация Базис-А', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2163_Stitch.jpg', 43.2505989, ' Абылай хана/Кабанбай батыра', 76.9429016, 'Столичный Центр', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21174, 'Globus Invest Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_1899_50092e5d476eb.jpg', 43.2439003, ' Муканова/Шевченко', 76.9156036, 'Театральный', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21175, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_1774_500f67fc280bd_55c890045ff17.jpg', 43.2519989, ' Муратбаева/Богенбай батыра', 76.918602, 'Фаворит', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21176, 'Столичная Недвижимость', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_SHan_rak.png', 43.2452011, ' Шевченко/Наурызбай батыра', 76.9371033, 'Шанырак', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21177, 'Урим Констракшн', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/508002_50f91aaabfc03.jpg', 43.1991997, ' Саина/Аскарова', 76.8684006, 'Apple Town', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21178, 'Рамс Казахстан', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Park_House.png', 43.2196999, ' Шаляпина/Алтынсарина', 76.8660965, 'Park House', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21179, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_doma_2.JPG', 43.1945, ' Аль-Фараби/Аскарова', 76.8694, 'Sun Villa', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21180, '', NULL, 43.2014008, ' Шаляпина/Ауэзова', 76.8187027, 'Алатау (Коттеджный городок)', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21181, 'Строй Сервис', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/29854ec4d907d440-02_56c6e38378c35.jpg', 43.2066002, '', 76.810997, 'Алатау Плюс (Коттеджный городок)', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21335, 'Дом Строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_askarbek.jpg', 43.225399, ' Кажымукана, 39', 76.9572983, 'Аскарбек', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21182, 'МАК-Алматы Гор Строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_7060_50ffaf6b0e177.jpg', 43.2015991, ' Момышулы/Шаляпина', 76.8427963, 'Алматы Гор Строй мкр. Мамыр-5,6', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21183, 'Астана Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Ar_stan.png', 43.2310982, ' Аксай-5 мкр-н, Садвакасова', 76.8311005, 'Арыстан', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21184, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_7066_50ffaad341e7c_55c96d5e997a5.jpg', 43.2196999, ' Абая/Момышулы', 76.8421021, 'Бейбарыс Билдинг по Абая/Момышулы', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21185, 'Алматы-Бетон', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_7042_50faa1aa75a8b.JPG', 43.2210007, ' Абая/Саина', 76.8467026, 'Виктория', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21186, 'КазКИР', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/gl_Jet_su_3_511383f745f0e.jpg', 43.2204018, ' Жетысу-3 мкр-н', 76.8404999, 'Жетысу', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21187, 'Матай Групп', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2419_500f70c5700d1.jpg', 43.203701, ' Рыскулбекова/Саина', 76.8666, 'Каргалы', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21188, 'Bayterek 2050', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Kuan_sh.png', 43.2332001, ' 2 мкр-н, 40г', 76.8599014, 'Куаныш', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21189, 'Корпорация Базис-А', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Mirn_i.png', 43.212101, ' Сулейменова/Пятницкого', 76.8779984, 'Мирный', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21190, 'Астана Курылыс', NULL, 43.2282982, ' Момышулы/Маречека', 76.8323975, 'мкр. Аксай-5', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21191, 'МАК-Алматы Гор Строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2392_50faa0de37314.jpg', 43.2202988, ' Жетысу-2 мкр-н', 76.8486023, 'мкр. Жетысу-2', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21192, 'Предприятие капитального строительства Акимата г. Алматы', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_mkr__Jet_su-3.png', 43.2184982, ' Жетысу-3 мкр-н', 76.8414001, 'мкр. Жетысу-3', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21193, 'Астана Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_mkr__Taugul-2.png', 43.2076988, ' Рыскулбекова/Навои', 76.8835983, 'мкр. Таугуль-2', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21194, 'ВЕК', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2494_5009292bbdf6c.jpg', 43.218399, ' Навои/Жандосова', 76.8803024, 'Науаи', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21195, 'Нико', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/4524_55cc663525faf.png', 43.2420006, ' Момышулы/Райымбека', 76.8262024, 'Нико по Момышулы/Райымбека', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21196, 'Нико', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_7067_510203d30fdc9.jpg', 43.2136002, ' Саина/Шаляпина', 76.8555984, 'Нико по Саина/Шаляпина', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21197, 'Нико', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/niko-po-toktabaeva-navoi-edit.jpg', 43.2113991, ' Токтабаева/Навои', 76.8822021, 'Нико по Токтабаева/Навои', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21198, 'Нико', NULL, 43.2396011, ' Толе би/Момышулы', 76.8286972, 'Нико по Толе би/Момышулы', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21199, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK-Otrar-Almat_-835x467.jpg', 43.2117004, ' Навои, 52', 76.882103, 'Отрар', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21200, 'Астана Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/abay_momysh_jpg2.jpg', 43.216301, ' Абая/Момышулы', 76.8391037, 'по Абая/Момышулы', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21201, 'Астана Курылыс', NULL, 43.2075996, ' Навои/Рыскулбекова', 76.8834991, 'по Навои/Рыскулбекова', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21202, 'Астана Курылыс', NULL, 43.2384987, ' Толе би/Яссауи', 76.8244019, 'по Толе би/Яссауи', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21203, 'Рамс Казахстан', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/rams_mam_2.JPG', 43.2131996, ' Мамыр-7 мкр-н', 76.8391037, 'Рамс мкр. Мамыр-7', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21204, 'Рамс Казахстан', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/tastak_rams_3JPG.JPG', 43.2546005, ' Фурката', 76.8811035, 'Рамс мкр. Тастак-3', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21205, 'Астана Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/astana_kyrilis.jpg', 43.2415009, ' Утеген батыра, 84г', 76.8607025, 'Сайран', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21206, 'Alshyn', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK__am_a__Do_a_.png', 43.2420006, ' Аксай-1 мкр-н', 76.8333969, 'Самга', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21207, 'Астана Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK__putnik.png', 43.2122002, ' Мамыр-1 мкр-н', 76.8431015, 'Спутник', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21208, 'Бейбарыс Билдинг', NULL, 43.2127991, ' Пятницкого/Мустафина', 76.8752975, 'Тау Нур', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21209, 'Global Building Contract', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2479_50fa3f9b28600.jpg', 43.2162018, ' Джандосова/Навои', 76.8825989, 'Тау Самал по Навои', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21210, 'Бизнес Проспект', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2404_50092dd45215b.jpg', 43.2136993, ' Шаляпина/Саина', 76.8555984, 'Тау Терек', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21211, 'Global Building Contract', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_tobe.jpg', 43.2304001, ' Правды/Улугбека', 76.8527985, 'Уштобе', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21212, '', NULL, 43.2436981, ' Момышулы/Райымбека', 76.8261032, 'Школьник', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21213, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_3902-002_51ff70aac09b9_55b0ab6edb3e4.jpg', 43.2444992, ' Толе би/Утеген Батыра', 76.8531036, 'Эдельвейс', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21214, 'Nurol Construction', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/__54_iz_59_.jpg', 43.2388992, ' Абая/Манаса', 76.9098969, 'Abay Residence', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21215, 'APMG, Stan Holding', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Adal_risedence.JPG', 43.2249985, ' Маркова/Аль-Фараби', 76.9367981, 'Adal Residence', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21216, 'Global Building Contract', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/9_5257db4bbe238.jpg', 43.2285004, ' Аль-Фараби/Желтоксан', 76.9437027, 'AFD Plaza', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21217, 'Компания ТТ', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/3_50fbfb6e36896.jpg', 43.1753998, ' Навои', 76.889801, 'Artville Алмарасан (Коттеджный городок)', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21218, 'Best Project', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/560054_510899e37ec9b.jpg', 43.1903992, ' Акиык, 12', 76.9068985, 'Baganashil Seven Stars', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21219, 'Turkuaz Invest', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/bayterek_50fc0712357dc.png', 43.2076988, ' Аль-Фараби/Ходжанова', 76.9104996, 'Bayterek Residence', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21220, 'TS Engineering', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/__46_iz_59__55b8bd4d6e509.jpg', 43.2345009, ' Бухар Жырау/Маркова', 76.9238968, 'Bukhar Zhyrau Towers', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35210, NULL, NULL, NULL, NULL, NULL, 'Бельбулак (КГ)', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35218, NULL, NULL, NULL, NULL, NULL, 'ВЕК на Байганина', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21221, 'Рамс Казахстан', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Risunok1.jpg', 43.2345009, ' Мусрепова', 76.9194031, 'Central Esentai Residence', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21222, 'Атрикс-Строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/esentai_50fbe749dde44.png', 43.2219009, ' Аль-Фараби/Шашкина', 76.9358978, 'Esentai Palace', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21223, 'Центрпласт', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/imporio_50fa481a29ccd.jpg', 43.2257996, ' Розыбакиева/Тимирязева', 76.8902969, 'Imporio', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21224, 'Рамс Казахстан', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Izumrud_Residence.png', 43.2155991, ' Жарокова', 76.9031982, 'Izumrud Residence', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21225, 'NIMR', NULL, 43.1856003, ' Аль-Фараби', 76.9280014, 'Kaplan House', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21226, 'Корпорация Базис-А', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/6874645_55b8be680e499.jpg', 43.2034988, ' Каблукова/Розыбакиева', 76.8904037, 'Megatower Almaty', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21227, 'Global Building Contract', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Miras_Park.png', 43.1897011, ' Дулати/Саина', 76.8749008, 'Miras Park', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21228, 'Group ETK', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2076_Stitch.jpg', 43.2008018, ' Гагарина/Кожабекова', 76.8986969, 'Real Almaty', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21229, 'Корпорация Базис-А', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/jk-4.jpg', 43.1489983, ' Дулати', 76.9052963, 'Royal Gardens', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21230, 'Сэт-Жол', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/1_50fccd01da928.jpg', 43.1948013, ' Аль-Фараби', 76.9300995, 'Sakura Home', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21231, 'Тенгиз Строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_0040_50fcd09718343.jpg', 43.235199, ' Сатпаева/Шагабутдинова', 76.9216003, 'Tengiz Towers', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21232, 'МАК-Алматы Гор Строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Alatau.png', 43.2263985, ' Маркова/Нахимова', 76.9348984, 'Алатау', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21233, 'МАК-Алматы Гор Строй', NULL, 43.2131004, ' Аль-Фараби', 76.9225998, 'Алтын Есик', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21234, 'Алматы Центр Строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_1154_Stitch.jpg', 43.237999, ' Сатпаева, 9б', 76.933403, 'Алтын Заман', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21235, 'МАК-Алматы Гор Строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2543_5006a1a02ed93.jpg', 43.2081985, ' Ходжанова/Аль-Фараби', 76.9113007, 'Алтын Орда', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21236, 'Корпорация Базис-А', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/phpThumb_generated_thumbnailjpg_50fbe426986e3.jpg', 43.1507988, ' Дулати', 76.9064026, 'Альпийские Луга (Коттеджный городок)', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21237, 'Атрикс-Строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Altair.png', 43.1478004, ' Дулати', 76.8964996, 'Альтаир', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21238, 'Аксай-Инвест', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_1966_500cd33593d3f.jpg', 43.2364998, ' Сатпаева/Жарокова', 76.8977966, 'Арай', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21239, 'Aria Project', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/3egds2f6tkc_520c9ffed8b23.jpg', 43.1973991, ' Казахфильм, 44б', 76.9094009, 'Ария', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21240, 'Дом Строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/full_50fc0e3b1ab90.jpg', 43.1837006, ' Ремизовка', 76.9431, 'Асем Тау (Коттеджный городок)', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21241, 'Интер Строй HC', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/1_525e14b565000.JPG', 43.1702995, ' Мустафина, 83', 76.8744965, 'Аскар Тау', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21242, 'Астана Недвижимость', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_50f9b83b2ffba.jpg', 43.2234993, ' Аль-Фараби', 76.9357986, 'Астана', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21243, 'BI Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_As_l_Tau.jpg', 43.2313004, ' Егизбаева, 7а', 76.8859024, 'Асыл Тау', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21244, 'Элитстрой', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/613010_50fa496f74a0e.jpg', 43.2272987, ' Тимирязева/Манаса', 76.9088974, 'Атакент', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21245, 'Алматы Центр Строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2549_510241d31737e_55c97ea37b504.jpg', 43.2084007, ' Аль-Фараби/Ходжанова', 76.9140015, 'Аэлита', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21246, 'Alshyn', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Bai-Tal.png', 43.203701, ' Рыскулбекова, 28/1', 76.8720016, 'Бай-Тал', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21247, 'Сэт-Жол', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2103_Stitch.jpg', 43.2006989, ' Баганашыл мкр-н, Санаторная', 76.9154968, 'Байсал', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21248, '', NULL, 43.2178993, ' Розыбакиева/Джандосова', 76.9083023, 'Байтерек (Коттеджный городок)', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21249, 'Атамекен Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_1928_500f6bafd45b7.jpg', 43.2364006, ' Гагарина/Мынбаева', 76.8937988, 'Бес Тулга', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21250, 'Рамс Казахстан', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Botanicheskii_sad.jpg', 43.2310982, ' Ауэзова, 163а', 76.9039001, 'Ботанический Бульвар', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21251, 'Корпорация Базис-А', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/551030_50f9b9b6bb745.jpg', 43.2150993, ' Ботанический сад/Экспериментальная', 76.910202, 'Ботанический Сад', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21252, 'Атрикс-Строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Brodvei.png', 43.2286987, ' Фурманова/Аль-Фараби', 76.9509964, 'Бродвей', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21253, 'Астана Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Vavilon.png', 43.2042999, ' Розыбакиева, 247', 76.8936996, 'Вавилон', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21254, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2472_Stitch.jpg', 43.2114983, ' Навои/Токтабаева', 76.8828964, 'Гармония', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21255, 'MAG Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_1685_Stitch.jpg', 43.2374992, ' Абая, 150', 76.8834, 'Гаухартас', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21256, 'МАК-Алматы Гор Строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_1206_50092534e75ac_55e531b38d311.jpg', 43.2247009, ' Аль-Фараби/Маркова', 76.9369965, 'Горстрой по Аль-Фараби/Маркова', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21257, 'Даймонд Групп', NULL, 43.1968002, ' Аль-Фараби/Розыбакиева', 76.8934021, 'Даймонд Групп', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21258, 'Корпорация Базис-А', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/__40_iz_59__55c9804135da7.jpg', 43.2397003, ' Сатпаева/Байсеитова', 76.9458008, 'Два Богатыря', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21259, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/21354ec4c617812f-deputett_55cc1d64a2cd4.jpg', 43.2074013, ' Аль-Фараби/Ходжанова', 76.9132996, 'Депутатский', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35231, NULL, NULL, NULL, NULL, NULL, 'Гранд Алатау', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21260, 'Корпорация Базис-А', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2554_500543bc4c8e9.jpg', 43.1865997, ' Мирас мкр-н', 76.8771973, 'Долина Роз', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21261, 'Capital Partners', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2429_500f6eff5b03b_55c9821577f0b.jpg', 43.2046013, ' Мустафина/Рыскулбекова', 76.8770981, 'Дуэт', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21262, 'Capital Partners', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_1253_Stitch.jpg', 43.2196999, ' Аль-Фараби', 76.9293976, 'Есентай Парк', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21263, 'Алматы Центр Строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/600_121116_1353054651_2_5146fade3d08d.jpg', 43.2321014, ' Каратаева', 76.941597, 'Жана Гасыр', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21264, 'Атрикс-Строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_1370_Stitch.jpg', 43.2322998, ' Фурманова/Ганди', 76.9484024, 'Жеруйык', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21265, 'Корпорация Базис-А', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/556035_50fa3639e3e96.jpg', 43.2366982, ' Сатпаева/Минина', 76.9253998, 'Звезда Востока', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21266, 'Таймас', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/4_510b8fe416f6f.jpg', 43.2061005, ' Аль-Фараби', 76.9301987, 'Зеленые Холмы (Коттеджный городок)', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21267, 'ВЕК', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_1176_500f70feaac19.jpg', 43.2349014, ' Тимирязева/Наурызбай батыра', 76.9375992, 'Керемет', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21268, 'Куат', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_1994_Stitch_5184c99ca2b2a_55c982732c133.jpg', 43.206501, ' Ескараева/Хусаинова', 76.8917999, 'Куат по Ескараева/Хусаинова', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21269, 'Куат', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_1134_Stitch.jpg', 43.2405014, ' Масанчи/Абая', 76.9321976, 'Куат по Масанчи/Абая', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21270, 'Куат', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2439_Stitch_55c982ecc400d.jpg', 43.2030983, ' Мустафина/Торайгырова', 76.8783035, 'Куат по Мустафина/Торайгырова', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21271, 'Куат', NULL, 43.2070007, ' Розыбакиева/Ескараева', 76.8919983, 'Куат по Розыбакиева/Ескараева', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21272, 'Куат', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_1914_Stitch_51021865af22f_55c9862286234.jpg', 43.2336998, ' Сатпаева/Гагарина', 76.8932037, 'Куат по Сатпаева/Гагарина', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21273, 'Куат', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2437_Stitch_50f926f243320_55c9867406dc7.jpg', 43.2019997, ' Торайгырова/Саина', 76.8730011, 'Куат по Торайгырова/Саина', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21274, 'Alshyn', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Kulan.png', 43.1986008, ' Орбита-3 мкр-н', 76.8749008, 'Кулан', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21275, 'Даниэл Констракшн', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2053_Stitch.jpg', 43.1958008, ' Розыбакиева/Аль-Фараби', 76.8928986, 'Лайлек', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21276, '', NULL, 43.2248993, ' Победы/Тимирязева', 76.8895035, 'Максат', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21277, 'Alshyn', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Vahtangovo.png', 43.2261009, ' Вахтангова, 17б', 76.8859024, 'на Вахтангова', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21278, 'Сэт-Жол', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/1e650aee_resizedScaled_516to345_50fa5306171e9.jpg', 43.1953011, ' Аль-Фараби', 76.9093018, 'Наурыз', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21279, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/24854ec4b2dc5ca8-novyimir_55cc2a5b966c7.jpg', 43.2172012, ' Розыбакиева, 250б', 76.893898, 'Новый Мир', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21280, 'ВЕК', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2497_500929f37e253_55c986f1c86bf.jpg', 43.2182999, ' Розыбакиева/Утепова', 76.8939972, 'Новый Центр', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21281, 'Корпорация Базис-А', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_1307_500e854323682.jpg', 43.2304001, ' Аль-Фараби/Фурманова', 76.9459991, 'Нурлы Тау', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21282, 'Корпорация Базис-А', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2587_501601984c169_55c9947e0ef17.jpg', 43.2411003, ' Абая/Наурызбай батыра', 76.9384003, 'Овация', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21283, '', NULL, 43.1960983, ' Аль-Фараби/Розыбакиева', 76.8951035, 'по Аль-Фараби/Розыбакиева', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21284, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2600_501606712fcb8.jpg', 43.2341003, ' Тимирязева', 76.9402008, 'по Тимирязева', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21285, 'Рамс Казахстан', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/rams_kz2.jpg', 43.2276993, ' Тимирязева/Шашкина', 76.9284973, 'Рамс Казахстан', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21286, 'Рамс Казахстан', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Rams_po_Abaia-Manasa.png', 43.2388992, ' Абая/Манаса', 76.9095993, 'Рамс по Абая/Манаса', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21287, 'Рамс Казахстан', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/po_Al-Farabi_-_Jarokova.png', 43.200901, ' Аль-Фараби/Жарокова', 76.9037018, 'Рамс по Аль-Фараби/Жарокова', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21288, 'Рамс Казахстан', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/rams_ayez_5jpg.jpg', 43.2310982, ' Бухар Жырау/Ауэзова', 76.9047012, 'Рамс по Бухар Жырау/Ауэзова', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21289, 'Рамс Казахстан', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Rams_po_Markova-Pirogova.png', 43.2305984, ' Маркова/Пирогова', 76.9293976, 'Рамс по Маркова/Пирогова', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21290, 'EMI', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/0386-300x199_5135d5a199d3b.jpg', 43.1982002, ' Аль-Фараби/Ремизовка', 76.9322968, 'Ремизовка', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21291, 'Global Invest', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_Rodniki1.png', 43.2060013, ' Жарокова/Дунаевского', 76.9070969, 'Родник', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21292, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/infrastruktura_w1_55cc2ccd4acc7.jpg', 43.1968994, ' Аль-Фараби', 76.9164963, 'Сан Таун (Коттеджный городок)', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21293, 'Корпорация Базис-А', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2115_51024a6994f05.jpg', 43.1875, ' Аскарова', 76.8669968, 'Солнечная Долина', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21294, 'Корпорация Базис-А', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/232_50fa3c7a37f70.jpg', 43.1973, ' Орбита-4 мкр-н', 76.8771973, 'Солнечная Долина (Коттеджный городок)', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21295, 'Столичная Недвижимость', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_1236_500e879610ddb.jpg', 43.2248993, ' Бальзака', 76.9337997, 'Солнечный Квартал', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21372, 'Серт', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/15654_55cb275323fa4.png', 43.2293015, ' Фурманова/Аль-Фараби', 76.950798, 'Сатти', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21296, 'Корпорация Базис-А', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2536_500f663027b51_55c995d97c889.jpg', 43.2069016, ' Аль-Фараби/Ходжанова', 76.9120026, 'Талисман', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21297, 'Билдинг Cервис', NULL, 43.1940994, ' Ремизовка', 76.9315033, 'Тау Коктем', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21298, 'Бейбарыс Продактс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/1_55e532f05189d.jpg', 43.2024002, ' Торайгырова/Мустафина', 76.8741989, 'Толе би Тау', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21299, 'ВЕК', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/tymar_50fc2ee61cbf4.jpg', 43.1999016, ' Аль-Фараби/Гагарина', 76.9005966, 'Тумар', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21300, 'Атрикс-Строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Ushele_Remizovka1.png', 43.2011986, ' Ремизовка', 76.9271011, 'Ущелье Ремизовка', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21301, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/65b0df23f-1024x768-4597432-orig_55cc33b4d8168.jpg', 43.2041016, ' Баганашыл мкр-н', 76.9266968, 'Хуторок', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21302, 'Корпорация Базис-А', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/951410446d55d222537c439d93c_56b84d4a7b2a7.jpg', 43.2060013, ' Навои', 76.8860016, 'Шахристан', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21303, 'East Star', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_SHolpan1_55e5342d8b97c.jpg', 43.2117004, ' Аль-Фараби', 76.9462967, 'Шолпан', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21304, 'Ahsel Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Edelveis.png', 43.2061996, ' Жамакаева', 76.9553986, 'Эдельвейс (Коттеджный городок)', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21305, 'Alwin Group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2587_501601984c169_55c9995e8e472.jpg', 43.2136993, ' Байкадамова/Гагарина', 76.8983994, 'Южный Дуэт', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21306, 'Куат', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/raymbeka_maxima.png', 43.2659988, ' Райымбека', 76.8995972, 'Максима по Райымбека', 83);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21307, 'Астана Курылыс', NULL, 43.3241005, ' Айнабулак-3 мкр-н', 76.9192963, 'мкр. Айнабулак-3', 83);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21308, 'МТС Компани', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_mkr__Dorojnik.jpg', 43.3059006, ' Дорожник мкр-н', 76.9014969, 'мкр. Дорожник', 83);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21309, 'Астана Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/kamaz_1.JPG', 43.3564987, ' Геологов', 76.9208984, 'мкр. Кокжиек', 83);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21310, 'МАК-Алматы Гор Строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/kulager_50fc1ce9b232f.jpg', 43.3050995, ' Омарова', 76.9225998, 'мкр. Кулагер', 83);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21311, 'Зер', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/P1080002_5620cb8a9f8ce.JPG', 43.2722015, ' Райымбека/Гончарова', 76.8927002, 'на Райымбека/Гончарова', 83);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21312, 'Астана Курылыс', NULL, 43.2942009, ' Достоевского, 11а', 76.9419022, 'по Достоевского', 83);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21313, 'Астана Курылыс', NULL, 43.2650986, ' Райымбека/Казакова', 76.9035034, 'по Райымбека/Казакова', 83);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21314, 'Куат', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_1584_50053a09f2ecd_55c9b26a18792.jpg', 43.2342987, ' Достык, 160', 76.9592972, 'Almaty Towers', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21315, 'Turkuaz Invest', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2634_501258afa8697.jpg', 43.1944008, ' Горная', 76.9891968, 'ArmanVille', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21316, 'Рамс Казахстан', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/to-chto-nado-1.jpg', 43.2602997, ' Абдуллиных/Каирбекова', 76.960701, 'Central Park Residence', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21317, 'TS Engineering', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/img_6024_50fbe6e30b15b.jpg', 43.1814003, ' Кыз Жибек', 76.8598022, 'Crystal Air Village (Коттеджный городок)', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21318, 'Евразия Строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_DeVille.png', 43.2220993, ' Омаровой/Рубинштейна', 76.9667969, 'DeVille', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21319, 'Global Building Contract', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Dostyk_Residence.png', 43.2206993, ' Рубинштейна/Достык', 76.9664001, 'Dostyk Residence', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21320, 'Парк Констракшн', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2228_Stitch__1__55b8b6e47e2a1.jpg', 43.2601013, ' Бузурбаева, 33', 76.9644012, 'Gorky Park Residence', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21321, 'Рамс Казахстан', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Rams_po_Furmanova.png', 43.2237015, ' Фурманова/Кажымукана', 76.9522018, 'Ile De France', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21322, 'Kusto Development', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Korkem_Tau_2_5124a09a63ce5.jpg', 43.2585983, ' Кок-тобе мкр-н, Восточная объездная (ВОАД)', 76.9821014, 'Koktobe City', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21323, 'Turkuaz Invest', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/__14_iz_59_.jpg', 43.2509995, ' Калдаякова/Кабанбай батыра', 76.9598007, 'Maxima Residence', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21324, 'Kablan', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Samal_Prestige.png', 43.2290993, ' Аль-Фараби/Мендыкулова', 76.9524002, 'Samal Prestige', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21325, 'Таймас', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/65049094_5417f57071bd5.jpg', 43.2302017, ' Снегина', 76.9552994, 'Samal Tower', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21326, 'Turkuaz Invest', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_1501_50125b33b6797_55c9b4300a581.jpg', 43.2262993, ' Аль-Фараби/Мендыкулова', 76.9545975, 'Turkuaz Tower', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21327, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2283_Stitch.jpg', 43.257, ' Казыбек би/Достык', 76.9558029, 'White Fort', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21328, 'Корпорация Базис-А', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/MKS_5787_50fbd8cae67eb.jpg', 43.2104988, ' Жамакаева, 258', 76.9455032, 'Ак Бота', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21329, 'Элитстрой', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2363_Stitch.jpg', 43.2484016, ' Жамбыла, 26', 76.9541016, 'Академия-1', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21330, 'Элитстрой', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2360_5006a0d96debd_55b8bf1a8e2eb.jpg', 43.2481995, ' Валиханова, 121', 76.9524994, 'Академия-2', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21331, 'Элитстрой', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/70_50fbdade0a957.JPG', 43.2071991, ' Достык/Оспанова', 76.9686966, 'Алем (Коттеджный городок)', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21332, 'ЖСК', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/10554ec54299c893-1-original_55b9969fa5215.jpg', 43.1978989, ' Горная, 103б', 76.9841003, 'Алмалы', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21333, 'Демалыс и Ко', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/__6_iz_59__55b999b31bfe5.jpg', 43.2411003, ' Сатпаева/Луганского', 76.9600983, 'Арман', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21334, 'Интер Строй HC', NULL, 43.2633018, ' Жибек жолы/Каирбекова', 76.9615021, 'Арман Жолдар', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35238, NULL, NULL, NULL, NULL, NULL, 'Дом на Утеген батыра', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21336, 'Корпорация Базис-А', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/phpThumb_generated_thumbnailjpg_50fa4a62e7fa1.jpeg', 43.2347984, ' Достык', 76.9577026, 'Базис по Достык', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21337, 'Корпорация Базис-А', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/full_50fa3377b783a.jpg', 43.2532005, ' Зенкова, 33', 76.9570007, 'Базис по Зенкова', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21338, 'Корпорация Базис-А', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2370_50064f22e19a4_55c9b8f4b7d80.jpg', 43.2473984, ' Шевченко/Зенкова', 76.9576035, 'Версаль', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21339, 'EMI', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/637008_5146eff787683.jpg', 43.2038994, ' Достык/Оспанова', 76.9766006, 'Горная Долина', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21340, 'МАК-Алматы Гор Строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Gor_stroi_samal_3.jpg', 43.2266006, ' Самал-3 мкр-н', 76.9564972, 'Горстрой мкр. Самал-3', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21341, 'МАК-Алматы Гор Строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_1499_500cda74bee33.jpg', 43.2266006, ' Кажымукана', 76.9600983, 'Горстрой по Хаджи Мукана', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21342, 'МАК-Алматы Гор Строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_1222_Stitch_50c9ba9e9977d__1__55c9b9e74db63.jpg', 43.2248993, ' Аль-Фараби/Маркова', 76.9378967, 'Горстрой-2 по Аль-Фараби/Маркова', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21343, 'Европолис', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_Evropolis_1.JPG', 43.2136002, ' Достык/Омаровой', 76.9792023, 'Европолис', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21344, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/janel_50fc13d0ad8bf.jpg', 43.2010002, ' Юбилейный п.', 76.9888, 'ЖанЕл (Коттеджный городок)', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21345, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/__17_iz_59_.jpg', 43.1994019, ' Ладушкина/Оспанова', 76.9729004, 'Зеленая Долина (Коттеджный городок)', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21346, 'Turkuaz Invest', NULL, 43.2248001, ' Фурманова/Кажымукана', 76.9533997, 'Иннова Резидентс', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21347, 'Куат', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/adb1906f5b27ad4b16d7788472af479d_516258673636f.jpg', 43.2305984, ' Аль-Фараби/Горная', 76.9665985, 'Кокше', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21348, 'Куат', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2220_Stitch_55c9baa52a2e6.jpg', 43.2607994, ' Барибаева/Гоголя', 76.9639969, 'Куат по Барибаева/Гоголя', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21349, 'Куат', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/c6be0b9c_resizedScaled_814to450_5101137f8cb92.jpg', 43.2397995, ' Достык/Сатпаева', 76.9580994, 'Куат по Достык/Сатпаева', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21350, 'Курылыс Коммерц', NULL, 43.2680016, ' Жангилдина/Янушкевича', 76.9581985, 'Люксембургский Сад (ЖК Оскар)', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21351, 'MAG group', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/2b7588106e6340de1175b6a55d67024c_50fa4d2fdd5a4.jpg', 43.2873993, ' Думан мкр-н', 77.0027008, 'Меркур Град', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21352, 'Астана Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_1436_50c9931e6473b.jpg', 43.2277985, ' Аль-Фараби/Мендыкулова', 76.9530029, 'Меркур Тауэрс', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21353, 'Нико', NULL, 43.2397003, ' Сатпаева/Чайковского', 76.9396973, 'Нико по Сатпаева/Чайковского', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21354, 'Нико', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/niko_abdyl.jpg', 43.2551994, ' Толе би/Абдуллиных', 76.960701, 'Нико по Толе би/Абдуллиных', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21355, 'Нико', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Bez_miann_i.png', 43.2556992, ' Толе би/Абдуллиных', 76.9609985, 'Нико-2 по Толе би/Абдуллиных', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21356, 'Корпорация Базис-А', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/__24_iz_59_.jpg', 43.2263985, ' Мендыкулова/Аль-Фараби', 76.9565964, 'Новый Мир (Коттеджный городок)', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21357, 'АО Имсталькон', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/nurtobe1_05aeb533689edb5108fab90e9ec00c1b_54d05e963f7ea.jpg', 43.1958008, ' Оспанова, 69/2', 76.9721985, 'Нур Тобе', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21358, 'Саулет Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2640_501600be418f8.jpg', 43.2657013, ' Есенберлина', 76.9733963, 'Парк Горького', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21359, 'Парк Констракшн', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_1285_Stitch.jpg', 43.2218018, ' Аль-Фараби/Шашкина', 76.9355011, 'Парк Резидентс', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21360, 'Элитстрой', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Pano_1_v2_1200_50066fbfbfc36.jpg', 43.2360992, ' Достык, 132', 76.9587021, 'Пионер', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21361, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_1511_500cd5102451a_55c9bee4502f6.jpg', 43.2291985, ' Аль-Фараби', 76.9580002, 'по Аль-Фараби', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21362, 'Элитстрой', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/po_Makataeva.png', 43.2652016, ' Макатаева/Жангилдина', 76.9582977, 'по Макатаева', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21363, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2338_Stitch.jpg', 43.2691002, ' Пушкина/Тургенская', 76.9525986, 'по Пушкина/Тургенская', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21364, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2181_Stitch_55c9bfc2ad47d.jpg', 43.2571983, ' Тулебаева/Казыбек би', 76.9473038, 'по Тулебаева/Советской', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21365, 'Ariyan Co.Ltd.', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/CrthGallery_516cd927cce7e.jpeg', 43.2840004, ' Думан-1 мкр-н', 77.0122986, 'Радуга (Коттеджный городок)', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21366, 'Корпорация Базис-А', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_1524_Stitch.jpg', 43.2258987, ' Кажымукана, 37', 76.9577026, 'Рапсодия', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21367, 'Корпорация Базис-А', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_1490_5006504a5f27d.jpg', 43.2230988, ' Фурманова/Кажымукана', 76.9527969, 'Ренессанс', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21368, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/overview_55cb2357696f2.jpg', 43.2202988, ' Аль-Фараби/Шашкина', 76.9332962, 'Ресми Групп', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21369, 'Элитстрой', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/__25_iz_59_.jpg', 43.2454987, ' Тулебаева, 175', 76.9489975, 'Салем', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21370, 'Алматы Центр Строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_1544_Stitch.jpg', 43.2300987, ' Аль-Фараби/Достык', 76.9596024, 'Самал Делюкс', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21371, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_1469_Stitch.jpg', 43.2242012, ' Фурманова/Кажымукана', 76.9539032, 'Сарканд', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (22013, NULL, NULL, 51.1936569, ' Карталинская, 29/1', 71.3828888, 'Молодая семья (Пана)', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21373, 'Тенгиз Строй', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2331_Stitch.jpg', 43.25, ' Зенкова/Кабанбай батыра', 76.9574966, 'Сункар', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21374, 'Элитстрой', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2181_Stitch_55c9c2c2a1c39.jpg', 43.2361984, ' Самал-1 мкр-н, 29', 76.9520035, 'Тау Самал по Мендыкулова', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21375, 'KazBuild Development', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_5300_5184e9e09d591.jpg', 43.2084999, ' Жамакаева, 254', 76.9618988, 'Тау Шатыр', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21376, 'Silkway Construction', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_1616_Stitch.jpg', 43.2369003, ' Сатпаева/Достык', 76.9599991, 'Терренкур', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21377, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/3-bogatyryab_50fc2d3b2dc0d.jpg', 43.2450981, ' Достык/Жамбыла', 76.9573975, 'Три Богатыря', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21378, 'Корпорация Базис-А', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/_triumf.jpg', 43.2266006, ' Аль-Фараби/Фурманова', 76.9496994, 'Триумф-1', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21379, 'Корпорация Базис-А', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/91054ec4ad3538df-2_55b7422b98ccf_55e533555e0ef.jpg', 43.2307014, ' Фурманова', 76.9511032, 'Триумф-2', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21380, 'Корпорация Базис-А', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_1390_Stitch.jpg', 43.2333984, ' Мендыкулова/Жолдасбекова', 76.9529037, 'Фантазия', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21381, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_1298_Stitch_55c9c343686b8.jpg', 43.219101, ' Аль-Фараби/Шашкина', 76.9348984, 'Цитадель', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21382, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2626_5012844d0528d.jpg', 43.2032013, ' Достык/Оспанова', 76.9773026, 'Эйвон', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21383, 'Элитстрой', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2269_Stitch_50c9951fb665c.jpg', 43.2542992, ' Богенбай батыра, 81', 76.9580002, 'Элит', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21384, 'Элитстрой', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2265_Stitch_50f9155e2b0b5_55c9c62c3deae.jpg', 43.2543983, ' Богенбай батыра, 79', 76.9586029, 'Элит-2', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21385, 'Корпорация Базис-А', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2614_501603da81410.jpg', 43.2216988, ' Достык/Омаровой', 76.9636002, 'Этюд', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21386, '', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/full__1_.jpg', 43.1811981, ' Карагайлы п.', 76.8380966, 'Алма Тау (Коттеджный городок)', 783);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21387, 'Аксу', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/91954ec4d61286b5-altynauyl1_55b9973737568.jpg', 43.2018013, ' Алтын-Ауыл мкр-н', 76.6604996, 'Алтын Ауыл', 783);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21388, 'Элитстрой', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/IMG_2574_500f69fd20e43.jpg', 43.1794014, ' Аскарова', 76.867897, 'Аль-Фараби', 783);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21389, 'Элитстрой', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/JK_As_l_Arman.png', 43.2307014, ' Иргели п., Райымбека', 76.7493973, 'Асыл Арман', 783);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21390, 'Кендала Строй Инвест', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/11804_Bella_Villa_bilbord_002.jpg', 43.1755981, ' Каргалинка п.', 76.8664017, 'Белла Вилла (Коттеджный городок)', 783);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21391, 'ТАМ Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/photo_65082_5146f8f1e8eed.jpg', 43.1819992, ' Толе би/Дулати', 76.8757019, 'Жайляу', 783);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21392, 'Таймас', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Olzha_51480b8a1cc71.JPG', 43.2326012, ' Райымбека/Ауэзова', 76.7966003, 'Премьера', 783);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21393, 'VerNur', NULL, 43.1624985, '', 76.792099, 'Тау Жолы (Коттеджный городок)', 783);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21394, 'Элитстрой', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/Han-Tengri.png', 43.1679001, ' п. Карагайлы, Мустафина, 54', 76.850502, 'Хан Тенгри', 783);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21395, 'Астана Курылыс', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/jyldiz_50fc1c8fa99b3.jpg', 43.3624001, ' Жулдыз-1 мкр-н', 76.9921036, 'мкр. Жулдыз', 99);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21396, 'Нико', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/ia_k.png', 43.3294983, ' Физкультурная/Сейфуллина', 76.9424973, 'Нико по Сейфуллина/Физкультурной', 99);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21397, '', NULL, 43.3321991, ' Молдагалиева/Акынов', 76.9504013, 'по Молдагалиева/Акынова', 99);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21398, '', NULL, 43.3536987, ' Спасская', 76.947403, 'по Спасской', 99);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (21399, 'Tauba Invest', 'http://www.kn.kz/uploads/cache/jk_list/landmark/landmark/rb_52e24565539a8.jpg', 43.3423996, ' Майлина, 54', 76.9938965, 'Радужный берег', 99);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (22000, 'Базис–А', 'http://a.photos.kr.kcdn.kz/content/98/71754ec4f0538042-1-400x300.jpg', 51.1152496, ' д.1 южнее ул. Шамши Калдаякова', 71.4496918, 'Английский Квартал', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (22019, 'BEK', 'http://a.photos.kr.kcdn.kz/content/29/781756a88eb82b4c9background2-bastau-400x300.jpg', 43.2474747, ' Казыбек би, уг. ул. Шагабутдинова', 76.9213104, 'Бастау', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (22012, 'Базисинвестстрой', 'http://a.photos.kr.kcdn.kz/content/1e/34254ec493094fbf-_romans_2-400x300.jpg', 51.1128235, ' Калдаякова - Нажимеденова', 71.4575043, 'Городской романс', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (22001, 'Табысты инвест ЖСК', 'http://a.photos.kr.kcdn.kz/content/b5/785156fe2dae220211-full-tabysty-400x300.jpg', 51.1232986, ' Коргалжынское шоссе – ул. Бейсековой', 71.4240036, 'Табысты', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (22023, 'BEAG Development ТОО', 'http://a.photos.kr.kcdn.kz/content/c4/771954efdf7f47c0589754ec62196d854-image-12-02-15-11-53-11-almatybay-tal-400x300.jpg', 43.2038307, ' Рыскулбекова, 28/1', 76.8717651, 'Бай–тал', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (22002, 'Pioneer Invest', 'http://a.photos.kr.kcdn.kz/content/60/38954ec571a6e8a1-img_1607-400x300.jpg', 51.1676674, ' Иманбаевой – Баянауыл', 71.4152527, 'Алмалы', 106);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (22003, 'Лея', 'http://a.photos.kr.kcdn.kz/content/2d/7773560c9fb99d0f0_8048-800-leya-komfort-400x300.jpg', 51.1806717, ' Ауэзова, д. 38/1', 71.4209061, 'Лея-Комфорт', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (22020, 'Тенгиз Строй', 'http://a.photos.kr.kcdn.kz/content/05/2676571fba300dee1_3719-almatytengiz-taujers-400x300.jpg', 43.2348518, ' Сатпаева — Кожамкулова', 76.9217224, 'Тенгиз Тауэрс', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (22004, NULL, 'http://a.photos.kr.kcdn.kz/content/c8/784756f8f5b88f917__3131-1-medik-400x300.jpg', 51.1813164, ' Желтоксан, 48/1 – ул. Московская', 71.4147186, 'Медик', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (22005, 'Caspian Service Kazakhstan', 'http://a.photos.kr.kcdn.kz/content/39/81954ec480da78ab-08-400x300.jpg', 51.1163254, ' Енбекшилер, дом 17', 71.4396896, 'Каспиан Палас', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (22006, NULL, 'http://a.photos.kr.kcdn.kz/content/47/781956ce9279a4fc41-seyfullina-9-1-400x300.jpg', 51.1697922, ' Сейфуллина, 9/1', 71.4056702, 'Сейфуллина, 9/1', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (22007, 'Лея', 'http://a.photos.kr.kcdn.kz/content/4b/7775560cb589cc6ef_8035-800-leya-sarybulak-400x300.jpg', 51.181118, ' Московская — ул. Сарыбулак', 71.4046478, 'Лея-Сарыбулак', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (22008, 'Лея', 'http://a.photos.kr.kcdn.kz/content/0b/777755de857fee83e_8020-1-leya-plyus-400x300.jpg', 51.1850815, ' Потанина', 71.4036407, 'Лея-плюс', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (22009, 'Толендi ЖСК', 'http://a.photos.kr.kcdn.kz/content/58/784156f5023417421__3039-1-tolendy-400x300.jpg', 51.1938477, ' Тлендиева, д. 50/1 – ул. Улытау', 71.3266983, 'Толенды', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (22010, NULL, 'http://a.photos.kr.kcdn.kz/content/18/87654ec48916f338-dscf1089-400x300.jpg', 51.1574593, ' Набережная реки Ишим', 71.4273376, 'Звезда Астаны', 107);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (22011, 'BI Group', 'http://a.photos.kr.kcdn.kz/content/a0/782556e0f0026360f05-kamal-400x300.jpg', 51.0954895, ' Хусейна бен Талала – ул. Орынбор', 71.4268417, 'Камал', 291);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (22026, 'Туркуаз Инвест', 'http://a.photos.kr.kcdn.kz/content/98/92054ec4acddfcd6-1-400x300.jpg', 43.2277832, ' Аль-Фараби - Мендыкулова', 76.9559708, 'Тюркуаз Тауэрс', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (22014, 'Эксклюзивстрой TOO', 'http://a.photos.kr.kcdn.kz/content/1f/776155b72269281f61-panorama-400x300.jpg', 43.2494354, ' Толе би, 273/5', 76.8680267, 'Панорама', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (22015, 'KUAT', 'http://a.photos.kr.kcdn.kz/content/93/2795571faf0bcef2b_5996-almatyalmaty-taujers-400x300.jpg', 43.2339592, ' Достык — Жолдасбекова', 76.9597397, 'Алматы Тауэрс', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (22021, 'Global Building Contract', 'http://a.photos.kr.kcdn.kz/content/a1/3084565ed91f831a411-almatyaltyn-bulak-2-400x300.jpg', 43.2415619, ' Брусиловского, 167', 76.8767624, 'Алтын Булак 2', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (22016, 'El Construction ТОО', 'http://a.photos.kr.kcdn.kz/content/f3/39454ec615409e31-foto_2-400x300.jpg', 43.237854, ' Саина, 19/1', 76.8407288, 'Атлас', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (22025, 'TS Engineering', 'http://a.photos.kr.kcdn.kz/content/54/2768571fb3a095fe3_3745-almatybuhar-zhyrau-taujers-400x300.jpg', 43.2343597, ' Маркова - Бухар жырау', 76.9232559, 'Бухар Жырау Тауэрс', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (22017, 'Global Building Contract', 'http://a.photos.kr.kcdn.kz/content/5b/3323567a34a58470e99054ec507c7143e-almatymanhetten-29-kvartal-400x300.jpg', 43.2426796, ' Брусиловского — Шакарима', 76.8772354, 'Манхеттан (29 квартал)', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (22022, 'Best Project', 'http://a.photos.kr.kcdn.kz/content/c3/17454ec4cd194023-01-400x300.jpg', 43.1903419, ' мкр Баганашыл', 76.9070435, 'Баганашил Севен Старс', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (22018, 'Корпорация Астана-Стройинвест ТОО', 'http://a.photos.kr.kcdn.kz/content/f8/28075693730a337596-almatyajgerim-400x300.jpg', 43.1962242, ' Розыбакиева, 289', 76.8923569, 'Айгерим', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (22024, 'Global Building Contract', 'http://bd-kz-01.appspot.com/a734/459/0/buildings/1070.jpg', 43.2386475, ' Варламова, 33', 76.872551, 'Жагалау - 46 квартал', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (22028, 'Бейбарыс Билдинг', 'http://a.photos.kr.kcdn.kz/content/97/15654ec4ca6c0638-2original-400x300.jpg', 43.2197838, ' мкр Жетысу-3, Абая - Момышулы', 76.8414841, 'Бейбарыс Билдинг', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (22027, 'Туркуаз Инвест', 'http://a.photos.kr.kcdn.kz/content/0b/82654ec4d1094653-1-original-400x300.jpg', 43.2038155, ' Достык — выше Оспанова', 76.9793396, 'Арман Вилла', 89);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (22031, NULL, 'http://a.photos.kr.kcdn.kz/content/15/71654ec619298d07-vid-400x300.jpg', NULL, ' Восточная', NULL, 'Green Landia', 73);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (22029, 'ВЕК', 'http://a.photos.kr.kcdn.kz/content/b6/32195680dd24f352f14-almatynavoi-400x300.jpg', 43.2168007, ' Навои,7', 76.8804779, 'ВЕК на Навои', 29);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (22030, NULL, 'http://a.photos.kr.kcdn.kz/content/08/35154ec60c0a38bc-vesna-400x300.jpg', 43.2569542, ' Муканова, 159', 76.913681, 'Весна', 26);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (34538, NULL, NULL, NULL, NULL, NULL, 'Altyn Tulip', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (34541, NULL, NULL, NULL, NULL, NULL, 'Arnau Premium', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (34545, NULL, NULL, NULL, NULL, NULL, 'Barcelona', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (34547, NULL, NULL, NULL, NULL, NULL, 'BI City Seoul', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (34550, NULL, NULL, NULL, NULL, NULL, 'Birlik House', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (34553, NULL, NULL, NULL, NULL, NULL, 'Castle House', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (34568, NULL, NULL, NULL, NULL, NULL, 'Family Park', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (34574, NULL, NULL, NULL, NULL, NULL, 'Golden Towers', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (34580, NULL, NULL, NULL, NULL, NULL, 'Nur Aspan', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (34608, NULL, NULL, NULL, NULL, NULL, 'Айдидар', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (34613, NULL, NULL, NULL, NULL, NULL, 'Ай Су', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (34617, NULL, NULL, NULL, NULL, NULL, 'Акбулак NAK', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (34639, NULL, NULL, NULL, NULL, NULL, 'Ален', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (34651, NULL, NULL, NULL, NULL, NULL, 'Алтын Орда-2', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (34674, NULL, NULL, NULL, NULL, NULL, 'Аристократ', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (34696, NULL, NULL, NULL, NULL, NULL, 'Бареева', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (34708, NULL, NULL, NULL, NULL, NULL, 'Верный-2', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (34734, NULL, NULL, NULL, NULL, NULL, 'Дарус', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (34738, NULL, NULL, NULL, NULL, NULL, 'Деңсаулық-Бақыт', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (34754, NULL, NULL, NULL, NULL, NULL, 'Елимай', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (34790, NULL, NULL, NULL, NULL, NULL, 'Жилой дом по ул. Сулуколь', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (34813, NULL, NULL, NULL, NULL, NULL, 'КазГЮА', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (34835, NULL, NULL, NULL, NULL, NULL, 'Комсомольский NAK', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (34850, NULL, NULL, NULL, NULL, NULL, 'Курылтай', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (34884, NULL, NULL, NULL, NULL, NULL, 'Мирас-2', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (34901, NULL, NULL, NULL, NULL, NULL, 'Норд', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (34914, NULL, NULL, NULL, NULL, NULL, 'Олжа–2', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (34939, NULL, NULL, NULL, NULL, NULL, 'По Кумисбекова', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (34941, NULL, NULL, NULL, NULL, NULL, 'По Манаса', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (34947, NULL, NULL, NULL, NULL, NULL, 'По Свердлова', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (34951, NULL, NULL, NULL, NULL, NULL, 'По Тлендиева-2', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (34957, NULL, NULL, NULL, NULL, NULL, 'Премьер', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (34961, NULL, NULL, NULL, NULL, NULL, 'Радиоточка', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (34969, NULL, NULL, NULL, NULL, NULL, 'Самал', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (34975, NULL, NULL, NULL, NULL, NULL, 'Сарайшик', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35001, NULL, NULL, NULL, NULL, NULL, 'Созвездие', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35010, NULL, NULL, NULL, NULL, NULL, 'Старый город', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35059, NULL, NULL, NULL, NULL, NULL, 'Шанырак на Дукенулы', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35069, NULL, NULL, NULL, NULL, NULL, 'Эдельвейс', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35080, NULL, NULL, NULL, NULL, NULL, 'ЮГ-1', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35085, NULL, NULL, NULL, NULL, NULL, 'Ясмин', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35087, NULL, NULL, NULL, NULL, NULL, 'Сары Париж', 105);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35094, NULL, NULL, NULL, NULL, NULL, 'Atameken Tower', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35097, NULL, NULL, NULL, NULL, NULL, 'Bella Villa (КГ)', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35105, NULL, NULL, NULL, NULL, NULL, 'Eco Green', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35107, NULL, NULL, NULL, NULL, NULL, 'Esentai Apartments', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35110, NULL, NULL, NULL, NULL, NULL, 'Exclusive Residence', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35112, NULL, NULL, NULL, NULL, NULL, 'Favorit', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35118, NULL, NULL, NULL, NULL, NULL, 'Luxushaus', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35121, NULL, NULL, NULL, NULL, NULL, 'NS – Towers', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35126, NULL, NULL, NULL, NULL, NULL, 'Royal', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35132, NULL, NULL, NULL, NULL, NULL, 'Sun City', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35134, NULL, NULL, NULL, NULL, NULL, 'Sun Garden', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35138, NULL, NULL, NULL, NULL, NULL, 'Zodiac', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35141, NULL, NULL, NULL, NULL, NULL, 'Айсулу', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35147, NULL, NULL, NULL, NULL, NULL, 'Ак жол - Отау', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35152, NULL, NULL, NULL, NULL, NULL, 'Аксай', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35159, NULL, NULL, NULL, NULL, NULL, 'Алма', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35171, NULL, NULL, NULL, NULL, NULL, 'Алматы Пана', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35176, NULL, NULL, NULL, NULL, NULL, 'Амани (КГ)', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35240, NULL, NULL, NULL, NULL, NULL, 'Достык, дом 290', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35242, NULL, NULL, NULL, NULL, NULL, 'Думан', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35247, NULL, NULL, NULL, NULL, NULL, 'Многофункциональный жилой комплекс Евразия', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35253, NULL, NULL, NULL, NULL, NULL, 'Жана Куат (КГ)', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35257, NULL, NULL, NULL, NULL, NULL, 'Жастар Mag', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35259, NULL, NULL, NULL, NULL, NULL, 'Жастар-2 Mag', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35265, NULL, NULL, NULL, NULL, NULL, 'Зеленая Долина', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35269, NULL, NULL, NULL, NULL, NULL, 'Импорио', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35272, NULL, NULL, NULL, NULL, NULL, 'Комфорт', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35277, NULL, NULL, NULL, NULL, NULL, 'Клубный дом DeVille', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35298, NULL, NULL, NULL, NULL, NULL, 'Максима Резидентс', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35301, NULL, NULL, NULL, NULL, NULL, 'МБ 52', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35307, NULL, NULL, NULL, NULL, NULL, 'Мечта на Минина', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35309, NULL, NULL, NULL, NULL, NULL, 'Мечта на Казыбек би', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35311, NULL, NULL, NULL, NULL, NULL, 'МЖК Мечта', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35314, NULL, NULL, NULL, NULL, NULL, 'Мкр Айнабулак-2', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35321, NULL, NULL, NULL, NULL, NULL, 'Мкр Мамыр-4', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35324, NULL, NULL, NULL, NULL, NULL, 'На Ладыгина', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35326, NULL, NULL, NULL, NULL, NULL, 'На Тулебаева', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35328, NULL, NULL, NULL, NULL, NULL, 'На Тимирязева — Желтоксан', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35331, NULL, NULL, NULL, NULL, NULL, 'Нахимова, 16', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35336, NULL, NULL, NULL, NULL, NULL, 'НИКО на Сатпаева - Луганского', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35345, NULL, NULL, NULL, NULL, NULL, 'Новый Мир на Радостовца - Утепова', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35347, NULL, NULL, NULL, NULL, NULL, 'Новый Мир-2 на Радостовца - Утепова', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35354, NULL, NULL, NULL, NULL, NULL, 'Олимп', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35376, NULL, NULL, NULL, NULL, NULL, 'По Советской', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35391, NULL, NULL, NULL, NULL, NULL, 'Рамс мкр Коктем по Тимирязева-Шашкина', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35394, NULL, NULL, NULL, NULL, NULL, 'Рамс Муратбаева - Кабанбай батыра', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35398, NULL, NULL, NULL, NULL, NULL, 'Реал Алматы', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35406, NULL, NULL, NULL, NULL, NULL, 'Салем-2', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35412, NULL, NULL, NULL, NULL, NULL, 'Сарыарка', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35415, NULL, NULL, NULL, NULL, NULL, 'Северное кольцо', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35417, NULL, NULL, NULL, NULL, NULL, 'Сезам', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35419, NULL, NULL, NULL, NULL, NULL, 'Симфония', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35424, NULL, NULL, NULL, NULL, NULL, 'Социал', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35430, NULL, NULL, NULL, NULL, NULL, 'Тан Нуры', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35432, NULL, NULL, NULL, NULL, NULL, 'Тараз', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35437, NULL, NULL, NULL, NULL, NULL, 'Тау Самал (КГ)', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35443, NULL, NULL, NULL, NULL, NULL, 'Таунхаус', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35467, NULL, NULL, NULL, NULL, NULL, 'Элит-1 на Зенкова', 2);
INSERT INTO apartment_complex (id, company, image_url, lat, location, lon, name, region_id) VALUES (35469, NULL, NULL, NULL, NULL, NULL, 'Элит-2 на Калдаякова', 2);


--
-- TOC entry 2382 (class 0 OID 87199)
-- Dependencies: 192
-- Data for Name: apartment_complex_krisha; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34529, '610', '6 микрорайон', 20385, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34530, '467', '13 магистраль', 20383, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34531, '717', '5 Звёзд', 20384, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34532, '406', '7 Бочек', 21082, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34533, '300', '7 Континент', 20562, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34534, '822', '7Я', 20386, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34535, '690', 'Akbulak Hills', 20387, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34536, '468', 'Akbulak Town', 20389, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34537, '696', 'Altyn Tulip', 34538, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34539, '683', 'Arman De Lux', 20919, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34540, '730', 'Arnau Premium', 34541, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34542, '798', 'Art House', 20920, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34543, '713', 'Asyl Park', 20921, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34544, '752', 'Barcelona', 34545, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34546, '779', 'BI City Seoul', 34547, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34548, '655', 'Bi Town', 20924, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34549, '826', 'Birlik House', 34550, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34551, '585', 'Bi-Village (КГ)', 20925, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34552, '634', 'Castle House', 34553, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34554, '789', 'Citylake', 20914, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34555, '680', 'Clubhouse', 20927, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34556, '750', 'Comfort House', 20928, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34557, '656', 'Comfort Town', 20929, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34558, '804', 'Crystal Apartments', 20391, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34559, '654', 'Deluxe Town', 20930, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34560, '699', 'Edel', 20815, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34561, '782', 'England', 20379, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34562, '819', 'Expo Boulevard', 20933, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34563, '645', 'Expo Сity', 20934, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34564, '784', 'Expo Plaza', 20915, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34565, '724', 'Expo Town', 20936, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34566, '832', 'Expo Towers', 20935, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34567, '805', 'Family Park', 34568, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34569, '758', 'Family Town', 20937, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34570, '586', 'Family Village (КГ)', 20938, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34571, '635', 'Future home Astana', 20392, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34572, '691', 'Garden Village', 20942, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34573, '746', 'Golden Towers', 34574, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34575, '192', 'Grand Alatau', 20842, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34576, '788', 'Highvill Ishim', 20393, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34577, '646', 'KingHouse', 20945, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34578, '633', 'Millennium Park', 20381, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34579, '768', 'Nur Aspan', 34580, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34581, '780', 'Park Avenue', 20948, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34582, '806', 'Pioneer', 20395, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34583, '781', 'Premier Palace', 20380, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34584, '777', 'Promenade Expo', 20917, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34585, '628', 'Rixos Khan Shatyr Residences', 20950, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34586, '801', 'Royal Expo Apartments', 20951, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34587, '763', 'Sky City', 20953, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34588, '715', 'Sultan apartaments', 20954, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34589, '142', 'Victoria Palace', 20955, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34590, '742', 'VIP-городок Саранда', 20956, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34591, '771', 'Vivere', 20958, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34592, '587', 'Viva Plaza', 20957, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34593, '469', 'Well House', 20959, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34594, '561', 'Абай-2', 20399, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34595, '143', 'Абай на Отырар - Габдуллина', 20397, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34596, '453', 'Абай на Победы - Абая', 20398, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34597, '144', 'Абылайхан', 20400, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34598, '145', 'Авангард', 20816, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34599, '146', 'Авиценна', 20961, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34600, '827', 'Авиценна-2', 20962, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34601, '626', 'Авицена-Элит', 20963, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34602, '380', 'Аврора', 20401, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34603, '147', 'Адель', 20402, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34604, '148', 'Азат', 20403, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34605, '362', 'Азия Плюс', 20404, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34606, '149', 'Айгерим', 20405, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34607, '817', 'Айдидар', 34608, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34609, '588', 'Айка', 20817, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34610, '150', 'Айсанам', 20966, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34692, '396', 'Байконур', 20438, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34611, '579', 'Айсанам De Luxe', 20967, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34612, '820', 'Ай Су', 34613, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34614, '347', 'Айя', 20406, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34615, '657', 'Академия', 20971, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34616, '792', 'Акбулак NAK', 34617, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34618, '151', 'Ак Бота', 20819, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34619, '152', 'Ак Булак', 20409, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34620, '471', 'Ак Булак-2', 20823, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34621, '472', 'Ак Булак-3', 20410, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34622, '466', 'Акбулак', 20409, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34623, '355', 'Ак Дала', 20407, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34624, '643', 'Акерке', 20825, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34625, '158', 'Ак Жайык', 20972, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34626, '589', 'Аккорд Плюс', 20413, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34627, '345', 'Ак Орда', 20968, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34628, '153', 'Ак Отау', 20820, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34629, '678', 'Ак Отау – 2', 20821, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34630, '154', 'Ак Сарай', 20969, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34631, '470', 'Ак Сарай-2', 20970, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34632, '753', 'Ак Тилек', 20822, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34633, '155', 'Ак Шанырак', 20408, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34634, '156', 'Академия на Кургалжинском шоссе', 20971, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34635, '381', 'Аккорд', 20412, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34636, '451', 'Аксу', 20414, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34637, '159', 'Алатау', 20973, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34638, '860', 'Ален', 34639, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34640, '700', 'Алмалы', 22002, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34641, '160', 'Алматы', 20974, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34642, '161', 'Алтай', 20415, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34643, '770', 'Алтын-Арна', 20975, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34644, '698', 'Алтын Булак', 20416, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34645, '162', 'Алтын Босага', 20827, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34646, '163', 'Алтын Гасыр', 20417, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34647, '164', 'Алтын Жиек', 20828, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34648, '625', 'Алтын Корган', 20418, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34649, '165', 'Алтын Орда', 20829, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34650, '838', 'Алтын Орда-2', 34651, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34652, '166', 'Алтын Раид', 20419, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34653, '703', 'Алтын Уя', 20420, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34654, '721', 'Алтын Шар', 20977, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34655, '409', 'Аль-Арка', 20830, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34656, '167', 'Альбион', 20978, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34657, '168', 'Альтаир', 20979, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34658, '169', 'Аль-Фараби', 20421, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34659, '170', 'Аль-Фараби-2', 20422, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34660, '171', 'Амангельды', 20423, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34661, '474', 'Английский квартал', 22000, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34662, '428', 'Анфилада', 20425, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34663, '837', 'Аниса-1', 20980, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34664, '172', 'Апато', 20426, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34665, '412', 'Апато Тауэр', 20427, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34666, '748', 'Араби', 20983, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34667, '473', 'Арабика', 20984, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34668, '173', 'Арай', 20429, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34669, '793', 'Арай-1', 20429, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34670, '794', 'Арай-2', 20985, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34671, '795', 'Арай-3', 20986, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34672, '174', 'Арайлым', 20987, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34673, '807', 'Аристократ', 34674, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34675, '348', 'Арман', 20831, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34676, '475', 'Арман кала', 20988, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34677, '692', 'Арнау', 20989, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34678, '175', 'Арыстан', 20430, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34679, '375', 'Асем Кала', 20990, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34680, '662', 'Асем Тас', 20991, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34681, '624', 'АСИ 33/23', 20992, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34682, '710', 'Аспан', 20431, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34683, '176', 'Астана жулдызы', 20832, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34684, '177', 'Астана Меруерт', 20432, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34685, '438', 'Астана Сити', 20433, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34686, '178', 'Атамекен', 20434, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34687, '393', 'Атриум', 20994, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34688, '760', 'Аулие-Ата', 20435, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34689, '424', 'Аурика', 20436, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34690, '446', 'Ашык Аспан', 20437, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34691, '179', 'Бавария', 20995, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34693, '180', 'Байконыс', 20439, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34694, '723', 'Бай Тау', 20996, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34695, '476', 'Бареева', 34696, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34697, '377', 'Барыс', 20441, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34698, '181', 'Батыр', 20833, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34699, '660', 'Бахус-2', 20444, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34700, '685', 'Баян Сулу', 20445, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34701, '182', 'Береке', 20447, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34702, '183', 'Богенбай Батыр', 20834, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34703, '184', 'Болашак', 20836, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34704, '652', 'Британский квартал', 20448, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34705, '185', 'Бурлин', 20837, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34706, '651', 'Верный', 20450, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34707, '693', 'Верный-2', 34708, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34709, '186', 'Версаль', 20997, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34710, '187', 'Визит', 20998, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34711, '188', 'Виктория', 20451, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34712, '189', 'Возрождение', 20839, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34713, '190', 'Восток', 20452, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34714, '855', 'Восток (Бахус)', 20453, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34715, '431', 'Восточный', 20455, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34716, '477', 'Восточный-2', 20456, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34717, '650', 'Времена года', 20999, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34718, '478', 'Гарант', 20840, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34719, '344', 'Гармония', 20841, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34720, '191', 'Городской романс', 22012, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34721, '193', 'Гранд Астана', 20457, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34722, '772', 'Гранд Астана Элит', 20457, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34723, '611', 'Градокомплекс-2', 21001, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34724, '612', 'Градокомплекс-3', 21002, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34725, '194', 'Гранитный', 20458, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34726, '195', 'Грация', 21003, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34727, '458', 'Грация Элит', 21004, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34728, '372', 'Грин виладж', 20943, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34729, '196', 'Гулдер', 20843, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34730, '379', 'Гулистан', 21005, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34731, '358', 'Дания', 21006, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34732, '479', 'Данияр', 21007, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34733, '415', 'Дарус', 34734, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34735, '410', 'Дебют', 20459, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34736, '197', 'Дельфин', 21008, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34737, '835', 'Деңсаулық-Бақыт', 34738, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34739, '402', 'Джунгарские Ворота', 20845, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34740, '198', 'Дипломат', 21009, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34741, '613', 'Домъ', 21010, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34742, '199', 'Достар', 20461, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34743, '480', 'Достар-2', 20462, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34744, '481', 'Достар-3', 20463, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34745, '200', 'Достык', 20464, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34746, '201', 'Дружба', 20846, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34747, '708', 'Дударай', 21011, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34748, '368', 'Дуэт', 20465, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34749, '403', 'Евразия', 20466, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34750, '384', 'Европа Палас', 20931, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34751, '202', 'Европейский', 20847, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34752, '421', 'Егемен', 20467, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34753, '839', 'Елимай', 34754, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34755, '203', 'Ельтай', 20848, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34756, '204', 'Ельтай-2', 20849, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34757, '205', 'Енлик', 20468, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34758, '727', 'Еркежан', 21013, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34759, '206', 'Есиль', 20469, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34760, '207', 'Жагалау', 20850, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34761, '482', 'Жагалау-2', 21014, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34871, '435', 'Мерей', 20519, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34762, '208', 'Жагалау-3', 21015, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34763, '671', 'Жагалау-4', 21016, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34764, '209', 'Жайдарман', 20851, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34765, '210', 'Жайна', 20470, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34766, '211', 'Жана Толкын', 21017, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34767, '590', 'Жанат', 20471, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34768, '212', 'Жансая', 21018, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34769, '353', 'Жануя', 20472, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34770, '213', 'Жар-Жар', 20473, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34771, '214', 'Жаркын', 20474, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34772, '632', 'Жарык', 21019, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34773, '215', 'Жас Кайрат', 20475, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34774, '216', 'Жас канат', 20476, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34775, '388', 'Жасмин', 20477, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34776, '217', 'Жастар', 20479, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34777, '218', 'Жастар-2', 20852, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34778, '219', 'Жастар-3', 20480, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34779, '483', 'Жастар-4', 20481, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34780, '342', 'Жасыл ел', 20482, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34781, '359', 'Железнодорожный', 20483, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34782, '220', 'Женис', 20853, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34783, '221', 'Жетi Жол', 20854, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34784, '769', 'Жетiген', 20484, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34785, '418', 'Жетысу', 20485, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34786, '357', 'Жибек Жолы', 20857, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34787, '450', 'Жигер', 20486, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34788, '591', 'Жигер-2', 20487, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34789, '741', 'Жилой дом по ул. Сулуколь', 34790, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34791, '796', 'Зам Зам', 21022, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34792, '222', 'Замечательный', 20488, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34793, '223', 'Запад', 20858, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34794, '425', 'Звезда Астаны', 22010, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34795, '722', 'Зелёный квартал', 21023, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34796, '673', 'Зере', 20859, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34797, '677', 'Зере – 2', 20490, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34798, '224', 'Зерде', 20489, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34799, '614', 'Золотой', 20492, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34800, '436', 'Золотой дом', 20492, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34801, '484', 'Золотая миля', 20491, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34802, '225', 'Изобилие', 20493, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34803, '440', 'Изумруд', 21024, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34804, '226', 'Изумрудный Квартал', 21025, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34805, '719', 'Империя', 20860, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34806, '227', 'Инфинити', 21026, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34807, '228', 'Инфинити-2', 21027, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34808, '399', 'Искандер', 20494, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34809, '665', 'Итальянский квартал', 20382, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34810, '229', 'Ишим', 21028, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34811, '230', 'Казахстан', 20495, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34812, '434', 'КазГЮА', 34813, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34814, '371', 'Кайнар', 20496, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34815, '485', 'Кайнар-2', 20497, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34816, '844', 'Камал', 22011, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34817, '369', 'Каминный', 20498, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34818, '361', 'Кампус', 20499, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34819, '231', 'Капитал', 20500, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34820, '232', 'Караван-1', 20501, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34821, '233', 'Караван-2', 20502, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34822, '664', 'Каражат', 20503, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34823, '351', 'Каскад', 21030, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34824, '234', 'Каспиан Палас', 22005, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34825, '235', 'Кахарман', 20504, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34826, '422', 'Кегок', 20505, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34827, '236', 'Кенесары', 20506, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34828, '237', 'Керемет', 21031, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34829, '486', 'Кайнар-2', 20497, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34830, '398', 'Китайская Стена', 20508, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34831, '238', 'Коксу', 20509, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34832, '852', 'Коктал-2', 20862, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34833, '364', 'Коктем', 20863, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34834, '790', 'Комсомольский NAK', 34835, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34836, '239', 'Комсомольский', 21033, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34837, '240', 'Комфорт', 21035, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34838, '697', 'Коргалжынский квартал', 21037, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34839, '668', 'Коркем', 21038, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34840, '734', 'Коркем Tower', 21039, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34841, '376', 'Красная деревня', 20510, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34842, '241', 'Краун Плаза', 21041, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34843, '423', 'Кукуруза', 20511, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34844, '242', 'Кулагер', 21043, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34845, '243', 'Кулан', 20512, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34846, '487', 'Кулан-2', 20513, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34847, '244', 'Кулан', 20512, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34848, '245', 'Кульсай', 21044, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34849, '809', 'Курылтай', 34850, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34851, '850', 'Кутты Мекен', 20864, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34852, '246', 'Кыз Жибек', 21045, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34853, '247', 'Кыпшак', 20514, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34854, '248', 'Лазурный квартал', 21046, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34855, '349', 'Лесная поляна (Косшы)', 21105, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34856, '249', 'Лея', 20865, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34857, '813', 'Лея-Комфорт', 22003, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34858, '814', 'Лея-Плюс', 22008, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34859, '815', 'Лея-Сарыбулак', 22007, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34860, '676', 'Лондон', 20946, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34861, '250', 'Магистральный', 20515, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34862, '615', 'Магистральный-3', 20516, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34863, '443', 'Максат', 20866, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34864, '251', 'Мария', 20517, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34865, '252', 'Махаббат', 20867, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34866, '253', 'Махаббат-2', 20868, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34867, '853', 'Махаббат-3', 20869, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34868, '254', 'Медео', 20518, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34869, '420', 'Медик', 22004, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34870, '644', 'Медина', 20870, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34872, '255', 'Мерей Интерконти', 20520, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34873, '256', 'Мереке', 20521, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34874, '257', 'Мереке-2', 20522, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34875, '592', 'Меретти', 20523, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34876, '258', 'Меруерт', 21049, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34877, '437', 'МЖК', 21050, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34878, '653', 'Микрорайон Достар - 3', 20463, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34879, '488', 'Миланский квартал', 20524, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34880, '259', 'Минима+', 20525, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34881, '720', 'Министерский', 21052, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34882, '260', 'Мирас', 20526, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34883, '261', 'Мирас-2', 34884, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34885, '427', 'Мирный', 20871, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34886, '262', 'Мисон', 21053, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34887, '489', 'Молодая семья', 20527, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34888, '263', 'Молодая семья-2', 20528, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34889, '849', 'Молодая семья (Пана)', 22013, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34890, '264', 'Монблан де Люкс', 20872, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34891, '265', 'Москва', 20529, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34892, '266', 'Мунар', 20530, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34893, '267', 'На Водно-зеленом бульваре', 21054, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34894, '407', 'Наурыз', 20532, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34895, '268', 'Независимость', 20533, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34896, '269', 'Нипи', 20534, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34897, '394', 'Новосел', 20535, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34898, '270', 'Новый мир', 21055, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34899, '271', 'Номад', 21056, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34900, '426', 'Норд', 34901, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34902, '490', 'Нур Канат', 21057, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34903, '701', 'Нур-Нур', 21058, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34904, '274', 'Нур Отау', 20537, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34905, '411', 'Нурай', 20538, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34906, '273', 'Нурканат', 21057, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34907, '341', 'Нурлы Дала', 21059, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34908, '783', 'Нурлы Уя', 21060, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34909, '346', 'Нурсая', 21061, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34910, '491', 'Нурсая Бонита', 21062, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34911, '616', 'Оберег', 20539, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34912, '275', 'Окжетпес', 20874, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34913, '787', 'Олжа–2', 34914, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34915, '276', 'Олимп палас', 21063, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34916, '277', 'Олимп палас-2', 21064, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34917, '278', 'Омир', 20540, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34918, '725', 'Омир Озен', 20541, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34919, '397', 'ОМК центр', 20875, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34920, '279', 'Орбита', 20542, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34921, '280', 'Орбита-2', 20543, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34922, '448', 'Ориенталь', 20545, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34923, '366', 'Орион', 21106, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34924, '674', 'Ордабасы', 20544, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34925, '281', 'Оркен де Люкс', 21065, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34926, '791', 'Орынбор', 21066, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34927, '282', 'Отандастар', 21067, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34928, '433', 'Отау', 20546, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34929, '439', 'Отель Дипломат', 21009, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34930, '283', 'Отрада', 20876, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34931, '284', 'Отырар', 21068, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34932, '672', 'Памир', 21069, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34933, '712', 'Пана', 20877, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34934, '492', 'Парижский Квартал', 20547, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34935, '684', 'Парковый', 21070, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34936, '493', 'По 12 Магистрали-Жумабаева', 20558, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34937, '593', 'По 188-ой улице', 20878, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34938, '494', 'По Кумисбекова', 34939, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34940, '495', 'По Манаса', 34941, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34942, '496', 'По Махтумкули', 20549, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34943, '497', 'По Мирзояна', 20550, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34944, '498', 'По Орынбор', 21071, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34945, '499', 'По Сарайшык', 21072, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34946, '500', 'По Свердлова', 34947, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34948, '501', 'По Сейфуллина', 20880, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34949, '502', 'По Тлендиева', 20881, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34950, '848', 'По Тлендиева-2', 34951, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34952, '503', 'По Ш.Кудайбердыулы', 20551, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34953, '617', 'По Янушкевича', 20552, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34954, '285', 'Победа', 20882, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34955, '286', 'Премиум', 21073, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34956, '718', 'Премьер', 34957, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34958, '694', 'Премьера', 21074, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34959, '287', 'Престиж', 20883, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34960, '858', 'Радиоточка', 34961, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34962, '288', 'Радуга', 21075, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34963, '289', 'Рахат', 20554, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34964, '658', 'Садовые кварталы', 20555, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34965, '290', 'Сайран', 20885, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34966, '728', 'Саламат', 20556, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34967, '417', 'Салтанат', 20557, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34968, '776', 'Самал', 34969, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34970, '400', 'Самрук', 20886, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34971, '386', 'Сан сити', 20396, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34972, '291', 'Сана', 20887, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34973, '594', 'Сапа-2007', 20888, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34974, '454', 'Сарайшик', 34975, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34976, '292', 'Сармат', 21076, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34977, '293', 'Сармат-2', 21077, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34978, '294', 'Сарыарка', 20889, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34979, '504', 'Сарыбулак', 20890, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34980, '295', 'Сатты', 20559, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34981, '363', 'Саулет', 20560, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34982, '416', 'Сауран', 21078, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34983, '378', 'Саяхат', 20891, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34984, '296', 'Светлый', 21079, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34985, '297', 'Свечки', 20892, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34986, '442', 'Север', 20893, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34987, '298', 'Северная корона', 21080, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34988, '299', 'Северное сияние', 21081, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34989, '505', 'Седьмой Континент', 20562, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34990, '419', 'Сезам', 20563, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34991, '841', 'Сейфуллина 9/1', 22006, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34992, '506', 'Семь Бочек', 21082, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34993, '670', 'Семь палат', 21083, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34994, '754', 'Сенатор', 20564, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34995, '301', 'Сеным', 21084, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34996, '302', 'Симфония', 20894, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34997, '441', 'Сказка', 20895, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34998, '303', 'Сказочный мир', 20565, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (34999, '367', 'Скиф', 20896, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35000, '704', 'Созвездие', 35001, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35002, '304', 'Солнечный', 20566, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35003, '305', 'Солнечный город', 20567, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35004, '618', 'Соло', 20568, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35005, '619', 'Социал', 20569, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35006, '507', 'Социум', 20570, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35007, '620', 'Союз Апполон', 21085, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35008, '306', 'Статус', 20897, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35009, '842', 'Старый город', 35010, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35011, '414', 'Степной', 20571, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35012, '307', 'Столичный', 20898, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35013, '308', 'Столичный-2', 20899, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35014, '462', 'Сулу', 20573, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35015, '595', 'Сымбат', 20574, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35016, '596', 'Талапкерский', 20900, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35017, '854', 'Табысты', 22001, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35018, '309', 'Тамыз', 20901, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35019, '310', 'Тараз', 20575, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35020, '456', 'Тархан', 20902, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35021, '659', 'Теремок', 20576, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35022, '311', 'Территория Комфорта', 21086, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35023, '312', 'Территория Комфорта-2', 20577, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35024, '313', 'Титаник', 20903, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35025, '597', 'Тлендиева', 20881, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35026, '314', 'Торлет', 20904, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35027, '851', 'Толенды', 22009, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35028, '315', 'Триумф Астаны', 21087, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35029, '764', 'Триумфальная Арка', 21088, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35030, '316', 'Триумфальный', 20916, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35031, '317', 'Тулпар', 21089, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35032, '621', 'Тулпар (Объединение-Сайран)', 20905, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35033, '318', 'Тулпар Элит', 21090, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35034, '319', 'Тумар', 21091, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35035, '356', 'Туран', 20578, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35036, '320', 'Туркестан', 20579, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35037, '321', 'Турсын Астана', 20580, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35038, '322', 'Улан', 20581, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35039, '323', 'Улытау', 20582, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35040, '432', 'Успех', 20906, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35041, '324', 'Уют', 21093, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35042, '325', 'Фаворит', 20583, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35043, '622', 'Факультет', 20584, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35044, '326', 'Фиеста', 20585, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35045, '327', 'Формула успеха', 20907, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35046, '374', 'Форт Строй', 20586, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35047, '354', 'Фортуна', 20587, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35048, '598', 'Французский Квартал', 20588, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35049, '328', 'Хайвил Астана', 20589, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35050, '732', 'Хайвил Парк', 20944, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35051, '329', 'Хан Тенгри', 20590, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35052, '843', 'Черёмушки', 21096, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35053, '445', 'Целиноград', 20908, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35054, '743', 'Целинаград − 2', 20909, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35055, '744', 'Целинаград − 3', 20910, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35056, '383', 'ЦМТ - Центр Международной Торговли', 21095, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35057, '330', 'Шанырак', 20591, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35058, '836', 'Шанырак на Дукенулы', 35059, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35060, '332', 'Шанырак-2', 20593, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35061, '331', 'Шанырак Премиум', 20592, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35062, '333', 'Шапагат нуры', 20594, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35063, '508', 'Шахар', 20596, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35064, '509', 'Шыгыс', 21097, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35065, '413', 'Ырыс', 20597, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35066, '395', 'Эверест', 20598, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35067, '343', 'Эгалите', 20599, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35068, '679', 'Эдельвейс', 35069, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35070, '334', 'Эдем', 21098, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35071, '335', 'Эдем палас', 20600, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35072, '336', 'Эдем палас-2', 20601, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35073, '711', 'Экодом', 21099, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35074, '681', 'Эксклюзив', 20911, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35075, '337', 'Эльнара', 20602, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35076, '338', 'Эмират', 20912, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35077, '370', 'Эталон', 20913, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35078, '339', 'Этюд', 20603, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35079, '510', 'ЮГ-1', 35080, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35081, '408', 'Юго-Восток', 20604, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35082, '387', 'Южный', 20605, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35083, '716', 'Янтарный', 21101, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35084, '802', 'Ясмин', 35085, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35086, '773', 'Сары Париж', 35087, 105);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35088, '1', 'Abay Residence', 21214, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35089, '647', 'Adal Residence', 21215, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35090, '599', 'AFD Plaza', 21216, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35091, '2', 'Apple Town', 21177, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35092, '3', 'Artville Алма-Арасан (КГ)', 21217, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35093, '572', 'Atameken Tower', 35094, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35095, '573', 'Bayterek Residence', 21219, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35096, '129', 'Bella Villa (КГ)', 35097, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35098, '825', 'Central Esentai Residence', 21221, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35099, '709', 'Central Park Residence', 21316, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35100, '130', 'Crystal Air Village (КГ)', 21317, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35101, '811', 'Dostyk Residence', 21319, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35102, '747', 'Green Landia', 22031, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35103, '4', 'Gorky Park Residence', 21320, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35104, '702', 'Eco Green', 35105, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35106, '51', 'Esentai Apartments', 35107, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35108, '574', 'Esentai Palace', 21222, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35109, '797', 'Exclusive Residence', 35110, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35111, '575', 'Favorit', 35112, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35113, '786', 'ile de France', 21321, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35114, '775', 'Izumrud Residence', 21224, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35115, '582', 'Kaplan House', 21225, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35116, '457', 'Koktobe city', 21322, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35117, '736', 'Luxushaus', 35118, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35119, '731', 'Miras Park', 21227, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35120, '824', 'NS – Towers', 35121, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35122, '609', 'Palm Valley', 21133, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35123, '749', 'Park House', 21178, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35124, '600', 'Real Almaty', 21228, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35125, '648', 'Royal', 35126, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35127, '687', 'Royal Gardens', 21229, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35128, '576', 'Sakura Home', 21230, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35129, '737', 'Samal Prestige', 21324, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35130, '745', 'Samal Tower', 21325, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35131, '577', 'Sun City', 35132, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35133, '816', 'Sun Garden', 35134, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35135, '639', 'Sun Villa', 21179, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35136, '6', 'White Fort', 21327, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35137, '803', 'Zodiac', 35138, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35139, '7', 'Айгерим', 22018, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35140, '766', 'Айсулу', 35141, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35142, '8', 'Ак Бота', 21328, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35143, '578', 'Акбулак', 21135, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35144, '10', 'Академия на Валиханова 121', 21330, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35145, '9', 'Академия на Жамбыла 26', 21329, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35146, '735', 'Ак жол - Отау', 35147, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35148, '765', 'Ак Алем', 21134, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35149, '11', 'АкКент', 21136, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35150, '12', 'Акмарал', 21141, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35151, '738', 'Аксай', 35152, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35153, '686', 'Алатау', 21232, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35154, '511', 'Алатау (КГ)', 21180, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35155, '389', 'Алатау Плюс (КГ)', 21181, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35156, '444', 'Алгабас (КГ)', 21137, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35157, '131', 'Алем (КГ)', 21331, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35158, '823', 'Алма', 35159, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35160, '132', 'Алма Тау', 21386, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35161, '14', 'Алма-Ата', 21142, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35162, '636', 'Алмалы', 21332, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35163, '15', 'Алматы Тауэрс', 22015, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35164, '405', 'Алтын Ауыл', 21387, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35165, '463', 'Алтын Булак 1', 21143, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35166, '464', 'Алтын Булак 2', 22021, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35167, '581', 'Алтын Есик', 21233, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35168, '16', 'Алтын Заман', 21234, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35169, '17', 'Алтын Орда', 21235, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35170, '705', 'Алматы Пана', 35171, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35172, '133', 'Альпийские Луга (КГ)', 21236, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35173, '19', 'Аль-Фараби', 21388, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35174, '606', 'Альтаир', 21237, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35175, '767', 'Амани (КГ)', 35176, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35177, '20', 'Арай', 21238, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35178, '629', 'Ария', 21239, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35179, '21', 'Арман', 21333, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35180, '22', 'Арман Вилла', 22027, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35181, '513', 'Арман Жолдар', 21334, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35182, '527', 'Арыстан', 21183, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35183, '134', 'Асем Тау (КГ)', 21240, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35184, '23', 'Аскар Тау', 21241, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35185, '24', 'Аскарбек', 21335, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35186, '25', 'Астана', 21242, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35187, '601', 'Асыл Тау', 21243, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35188, '706', 'Асыл Арман', 21389, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35189, '26', 'Ата', 21144, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35190, '514', 'Атакент', 21244, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35191, '740', 'Атлас', 22016, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35192, '29', 'Аэлита', 21245, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35193, '30', 'Баганашил Севен Старс', 22022, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35194, '515', 'Базис на Достык', 35195, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35196, '86', 'Базис на Зенкова 33', 35197, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35198, '87', 'Базис на Чайковского', 35199, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35200, '31', 'Байсал', 21247, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35201, '755', 'Бай–тал', 22023, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35202, '135', 'Байтерек (КГ)', 21248, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35203, '799', 'Бакытты омир', 35204, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35205, '840', 'Бастау', 22019, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35206, '757', 'Батыр', 35207, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35208, '32', 'Бейбарыс Билдинг', 22028, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35209, '649', 'Бельбулак (КГ)', 35210, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35211, '465', 'Береке', 21146, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35212, '34', 'Бес Тулга', 21249, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35213, '460', 'Ботанический Бульвар', 21250, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35214, '35', 'Ботанический Cад', 21251, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35215, '607', 'Бродвей', 21252, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35216, '36', 'Бухар Жырау Тауэрс', 22025, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35217, '38', 'ВЕК на Байганина', 35218, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35219, '39', 'ВЕК на Навои', 22029, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35220, '516', 'Версаль', 21338, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35221, '729', 'Весна', 22030, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35222, '739', 'Весновка', 21147, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35223, '40', 'Виктория', 21185, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35224, '675', 'Гармония', 21254, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35225, '41', 'Гаухартас', 21255, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35226, '42', 'Горная Долина', 21339, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35227, '43', 'Горстрой на Аль-Фараби - Маркова', 21256, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35228, '45', 'Горстрой на ул. Кажымухана, дом 4', 21341, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35229, '44', 'Горстрой Самал 3, дом 21', 21340, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35230, '580', 'Гранд Алатау', 35231, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35232, '340', 'Даймонд Групп', 21257, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35233, '365', 'Два Богатыря на Сатпаева - Байсеитова 49, 42', 21258, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35234, '46', 'Депутатский', 21259, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35235, '47', 'Долина роз', 21260, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35236, '689', 'Дом на Ботаническом бульваре', 21250, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35237, '761', 'Дом на Утеген батыра', 35238, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35239, '85', 'Достык, дом 290', 35240, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35241, '517', 'Думан', 35242, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35243, '48', 'Дуэт', 21261, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35244, '5', 'Европолис', 21343, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35245, '630', 'Евразия', 21148, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35246, '631', 'Многофункциональный жилой комплекс Евразия', 35247, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35248, '401', 'Жагалау - 46 квартал', 22024, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35249, '52', 'Жайлы', 21150, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35250, '53', 'Жайляу', 21391, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35251, '54', 'Жана Гасыр', 21263, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35252, '137', 'Жана Куат (КГ)', 35253, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35254, '138', 'ЖанЕл (КГ)', 21344, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35255, '663', 'Жасмин', 21151, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35256, '55', 'Жастар Mag', 35257, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35258, '518', 'Жастар-2 Mag', 35259, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35260, '56', 'Жастар Атамекен', 21153, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35261, '57', 'Жеруйык', 21264, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35262, '58', 'Жетысу', 21186, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35263, '59', 'Звезда Востока', 21265, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35264, '60', 'Зеленая Долина', 35265, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35266, '139', 'Зеленые Холмы (КГ)', 21266, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35267, '61', 'Изумрудный город', 21154, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35268, '429', 'Импорио', 35269, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35270, '519', 'Иннова Резидентс', 21346, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35271, '821', 'Комфорт', 35272, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35273, '62', 'Каргалы', 21187, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35274, '459', 'Каусар', 21155, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35275, '63', 'Керемет', 21267, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35276, '762', 'Клубный дом DeVille', 35277, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35278, '360', 'Кокжиек', 21032, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35279, '64', 'Кокше', 21347, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35280, '785', 'Комфорт Плюс', 21156, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35281, '857', 'Куаныш', 21188, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35282, '65', 'КУАT на Достык - Сатпаева', 21349, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35283, '66', 'КУАТ на Барибаева - Гоголя', 21348, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35284, '67', 'КУАТ на Ескараева - Хусаинова', 21268, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35285, '70', 'КУАТ на Масанчи - Абая', 21269, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35286, '520', 'КУАТ на Мустафина - Торайгырова', 21270, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35287, '68', 'КУАТ на Панфилова - Гоголя', 21157, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35288, '521', 'КУАТ на Розыбакиева - Ескараева', 21271, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35289, '350', 'КУАТ на Сатпаева - Гагарина', 21272, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35290, '392', 'КУАТ на Торайгырова - Саина', 21273, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35291, '69', 'КУАТ на Фурманова - Казыбек-би', 21158, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35292, '522', 'Лайлек', 21275, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35293, '523', 'Ласточкино гнездо', 21159, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35294, '524', 'Люксембургский Сад (ЖК Оскар)', 21350, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35295, '525', 'Максат', 21276, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35296, '71', 'Максима на Райымбека', 21306, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35297, '447', 'Максима Резидентс', 35298, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35299, '640', 'Манхеттан (29 квартал)', 22017, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35300, '808', 'МБ 52', 35301, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35302, '99', 'Мега Сайран', 21160, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35303, '72', 'Mega Tower Almaty', 21226, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35304, '73', 'Меркур Град', 21351, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35305, '74', 'Меркур Тауэрс', 21352, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35306, '667', 'Мечта на Минина', 35307, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35308, '774', 'Мечта на Казыбек би', 35309, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35310, '778', 'МЖК Мечта', 35311, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35312, '695', 'Мирный', 21189, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35313, '526', 'Мкр Айнабулак-2', 35314, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35315, '602', 'Мкр Дорожник', 21308, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35316, '528', 'Мкр Жетысу-2', 21191, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35317, '529', 'Мкр Жетысу-3', 21192, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35318, '530', 'Мкр Жулдыз', 21395, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35319, '531', 'Мкр Кулагер', 21310, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35320, '532', 'Мкр Мамыр-4', 35321, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35322, '533', 'На Байганина', 21161, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35323, '726', 'На Ладыгина', 35324, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35325, '759', 'На Тулебаева', 35326, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35327, '800', 'На Тимирязева — Желтоксан', 35328, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35329, '534', 'Науаи', 21194, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35330, '828', 'Нахимова, 16', 35331, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35332, '75', 'Наурыз', 21278, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35333, '77', 'НИКО Джамбула - Чайковского', 21162, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35334, '535', 'НИКО на Раимбека - Момышулы', 21195, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35335, '78', 'НИКО на Сатпаева - Луганского', 35336, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35337, '537', 'НИКО на Сатпаева - Чайковского', 21353, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35338, '538', 'НИКО на Сейфуллина - Физкультурной', 21396, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35339, '373', 'НИКО на Токтабаева - Навои', 21197, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35340, '79', 'НИКО на Толе би - Абдулиных', 21354, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35341, '385', 'НИКО на Толе би - Момышулы', 21198, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35342, '76', 'НИКО-2 на Толе би - Абдулиных', 21355, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35343, '140', 'Новый мир (КГ)', 21356, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35344, '81', 'Новый Мир на Радостовца - Утепова', 35345, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35346, '80', 'Новый Мир-2 на Радостовца - Утепова', 35347, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35348, '583', 'Новый Центр', 21280, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35349, '82', 'Ностальжи', 21163, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35350, '834', 'Нур Тобе', 21357, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35351, '83', 'Нурлы Тау', 21281, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35352, '84', 'Овация', 21282, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35353, '669', 'Олимп', 35354, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35355, '88', 'Отрар', 21199, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35356, '810', 'Панорама', 22014, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35357, '89', 'Парк Горького', 21358, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35358, '90', 'Парк Резиденс', 21359, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35359, '584', 'Персия', 21139, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35360, '91', 'Пионер', 21360, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35361, '92', 'Премьера', 21392, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35362, '539', 'Персия', 21139, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35363, '540', 'По Абая - Мамышулы', 21200, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35364, '541', 'По Аль-Фараби', 21361, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35365, '542', 'По Аль-Фараби - Розыбакиева', 21283, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35366, '543', 'По Достоевского', 21312, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35367, '544', 'По Жамбыла', 21165, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35368, '545', 'По Кабанбай-Батыра', 21166, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35369, '546', 'По Макатаева', 21362, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35370, '547', 'По Молдагалиева - Акынова', 21397, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35371, '548', 'По Навои - Рыскулбекова', 21201, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35372, '549', 'По Нурмакова', 21167, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35373, '550', 'По Пушкина - Тургенская', 21363, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35374, '551', 'По Раимбека - Казакова', 21313, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35375, '552', 'По Советской', 35376, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35377, '553', 'По Спасской', 21398, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35378, '554', 'По Тимирязева', 21284, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35379, '555', 'По Толе-Би - Яссауи', 21202, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35380, '556', 'По Тулебаева - Советской', 21364, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35381, '557', 'По Фурманова', 21168, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35382, '558', 'По Шагабудинова', 21169, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35383, '560', 'По Шевченко - Ауэзова', 21170, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35384, '93', 'Прогресс', 21171, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35385, '661', 'Радужный берег', 21399, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35386, '603', 'Рамс Абая - Манаса', 21286, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35387, '641', 'Рамс мкр Мамыр-7', 21203, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35388, '562', 'Рамс мкр Тастак-3', 21204, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35389, '563', 'Рамс мкр Казахстан', 21285, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35390, '627', 'Рамс мкр Коктем по Тимирязева-Шашкина', 35391, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35392, '564', 'Рамс Бухар - Жырау - Ауэзова', 21288, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35393, '565', 'Рамс Муратбаева - Кабанбай батыра', 35394, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35395, '94', 'Рапсодия', 21366, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35396, '95', 'Раушан', 21172, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35397, '96', 'Реал Алматы', 35398, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35399, '455', 'Ремизовка', 21290, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35400, '97', 'Ренессанс', 21367, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35401, '98', 'Ресми Групп', 21368, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35402, '666', 'Родник', 21291, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35403, '566', 'Сайран', 21205, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35404, '100', 'Салем', 21369, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35405, '859', 'Салем-2', 35406, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35407, '101', 'Самал де Люкс', 21370, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35408, '831', 'Самга', 21206, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35409, '141', 'Сантаун (КГ)', 21292, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35410, '567', 'Сарканд', 21371, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35411, '733', 'Сарыарка', 35412, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35413, '102', 'Саттэ', 21372, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35414, '818', 'Северное кольцо', 35415, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35416, '756', 'Сезам', 35417, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35418, '751', 'Симфония', 35419, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35420, '103', 'Солнечная Долина', 21293, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35421, '568', 'Солнечная Долина (КГ)', 21294, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35422, '104', 'Солнечный Квартал', 21295, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35423, '390', 'Социал', 35424, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35425, '512', 'Спутник', 21207, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35426, '105', 'Столичный Центр', 21173, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35427, '106', 'Сункар', 21373, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35428, '107', 'Талисман', 21296, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35429, '623', 'Тан Нуры', 35430, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35431, '812', 'Тараз', 35432, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35433, '637', 'Тау Жолы', 21393, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35434, '108', 'Тау Коктем', 21297, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35435, '604', 'Тау Нур', 21208, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35436, '688', 'Тау Самал (КГ)', 35437, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35438, '642', 'Тау Самал на Мендыкулова', 21374, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35439, '109', 'Тау Самал на Навои', 21209, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35440, '536', 'Тау Терек', 21210, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35441, '461', 'Тау Шатыр', 21375, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35442, '682', 'Таунхаус', 35443, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35444, '110', 'Театральный', 21174, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35445, '111', 'Тенгиз Тауэрс', 22020, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35446, '112', 'Терренкур', 21376, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35447, '113', 'Толе би Тау', 21298, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35448, '114', 'Три Богатыря', 21377, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35449, '115', 'Триумф 1', 21378, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35450, '116', 'Триумф 2', 21379, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35451, '570', 'Тумар', 21299, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35452, '117', 'Тюркуаз Тауэрс', 22026, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35453, '118', 'Уш-Тобе', 21211, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35454, '608', 'Ущелье Ремизовка', 21300, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35455, '119', 'Фантазия', 21380, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35456, '120', 'Хан-Тенгри', 21394, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35457, '391', 'Хуторок', 21301, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35458, '121', 'Цитадель', 21381, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35459, '122', 'Шанырак', 21176, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35460, '123', 'Шахристан', 21302, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35461, '571', 'Школьник', 21212, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35462, '605', 'Шолпан', 21303, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35463, '452', 'Эдельвейс', 21213, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35464, '124', 'Эдельвейс (КГ)', 21304, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35465, '430', 'Эйвон', 21382, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35466, '125', 'Элит-1 на Зенкова', 35467, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35468, '126', 'Элит-2 на Калдаякова', 35469, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35470, '127', 'Этюд', 21385, 2);
INSERT INTO apartment_complex_krisha (id, key, name, complex_id, region_id) VALUES (35471, '128', 'Южный Дуэт', 21305, 2);


--
-- TOC entry 2391 (class 0 OID 0)
-- Dependencies: 189
-- Name: apartment_complex_krisha_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('apartment_complex_krisha_seq', 1, false);


--
-- TOC entry 2392 (class 0 OID 0)
-- Dependencies: 190
-- Name: apartment_complex_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('apartment_complex_seq', 1, false);


--
-- TOC entry 2362 (class 0 OID 86867)
-- Dependencies: 172
-- Data for Name: crawler; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO crawler (id, created_date, modified_date, modifier_name, alias, dest_queue_name, last_source_scanned_time, last_usage, name, run_every, status, crawlergroup_id) VALUES (3, '2016-04-27 01:42:15.445878', '2016-04-27 01:42:15.445878', 'artem.demidovich', '3ROOM-ALL', 'adverts.krisha.sell.3room', '2016-05-07 02:18:26.912', '2016-04-27 01:42:15.445878', '3 Room flats', '30s', 'NOT_ACTIVE', 1);
INSERT INTO crawler (id, created_date, modified_date, modifier_name, alias, dest_queue_name, last_source_scanned_time, last_usage, name, run_every, status, crawlergroup_id) VALUES (2, '2016-04-27 01:41:39.668898', '2016-04-27 01:41:39.668898', 'artem.demidovich', '2ROOM-ALL', 'adverts.krisha.sell.2room', '2016-05-07 02:18:32.86', '2016-04-27 01:41:39.668898', '2 Room flats', '15s', 'NOT_ACTIVE', 1);
INSERT INTO crawler (id, created_date, modified_date, modifier_name, alias, dest_queue_name, last_source_scanned_time, last_usage, name, run_every, status, crawlergroup_id) VALUES (1, '2016-04-27 01:40:00.346689', '2016-04-27 01:40:00.346689', 'artem.demidovich', '1ROOM-ALL', 'adverts.krisha.sell.1room', '2016-05-07 02:18:30.063', '2016-04-27 01:40:00.346689', '1 Room flats', '20s', 'NOT_ACTIVE', 1);
INSERT INTO crawler (id, created_date, modified_date, modifier_name, alias, dest_queue_name, last_source_scanned_time, last_usage, name, run_every, status, crawlergroup_id) VALUES (5, '2016-05-07 21:53:00.685002', '2016-05-07 21:53:00.685002', 'artem.demidovich', 'ALL-FLATS-RENT', 'adverts.krisha.flat.rent', '2016-05-09 02:49:45.891', '2016-05-07 21:53:00.685002', 'All flat rent in KZ', '10s', 'ACTIVE', 1);
INSERT INTO crawler (id, created_date, modified_date, modifier_name, alias, dest_queue_name, last_source_scanned_time, last_usage, name, run_every, status, crawlergroup_id) VALUES (4, '2016-05-07 02:19:47.130378', '2016-05-07 02:19:47.130378', 'artem.demidovich', 'ALL-FLATS-SELL', 'adverts.krisha.flat.sell', '2016-05-09 02:49:49.638', '2016-05-07 02:20:27.692976', 'All flats in KZ', '15s', 'ACTIVE', 1);


--
-- TOC entry 2363 (class 0 OID 86875)
-- Dependencies: 173
-- Data for Name: crawler_groups; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO crawler_groups (id, created_date, modified_date, modifier_name, alias, description, name, source_system_base_url, source_system_type, status, use_proxy_servers, use_user_agents) VALUES (1, '2016-04-27 01:32:26.918835', '2016-04-27 01:32:26.918835', 'artem.demidovich', 'KRISHA', 'Выгрузка объявлений с крыши', 'Крыша.kz', 'https://api.krisha.kz/v1/adverts/search.json?appId=123892005472&appKey=0b94b147d7315ac8d23fdfcae24cb7a4&currentUser=&withAdverts=1', 'KRISHA', 'ACTIVE', true, true);
INSERT INTO crawler_groups (id, created_date, modified_date, modifier_name, alias, description, name, source_system_base_url, source_system_type, status, use_proxy_servers, use_user_agents) VALUES (2, '2016-04-27 01:33:15.584439', '2016-04-27 01:33:15.584439', 'artem.demidovich', 'KN', 'Выгрузка объявлений с kn.kz', 'kn.kz', 'https://api.krisha.kz/v1/adverts/search.json?appId=123892005472&appKey=0b94b147d7315ac8d23fdfcae24cb7a4&currentUser=&withAdverts=1', 'KN', 'NOT_ACTIVE', true, true);


--
-- TOC entry 2393 (class 0 OID 0)
-- Dependencies: 177
-- Name: crawler_groups_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('crawler_groups_seq', 1, false);


--
-- TOC entry 2364 (class 0 OID 86883)
-- Dependencies: 174
-- Data for Name: crawler_parameters; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO crawler_parameters (id, created_date, modified_date, modifier_name, description, key, name, status, value, crawler_id) VALUES (2, '2016-04-27 17:24:26.926772', '2016-04-27 17:24:26.926772', 'artem.demidovich', 'Регион объявлений', 'REGION', 'Регион Астана', 'ACTIVE', 'query[data][map.geo_id]=105', 1);
INSERT INTO crawler_parameters (id, created_date, modified_date, modifier_name, description, key, name, status, value, crawler_id) VALUES (4, '2016-04-27 17:30:08.282449', '2016-04-27 17:30:08.282449', 'artem.demidovich', 'Кол-во комнат от', 'ROOMS_FROM', 'Комнат от 1', 'ACTIVE', 'query[data][live.rooms][from]=1', 1);
INSERT INTO crawler_parameters (id, created_date, modified_date, modifier_name, description, key, name, status, value, crawler_id) VALUES (5, '2016-04-27 17:31:18.125241', '2016-04-27 17:31:18.125241', 'artem.demidovich', 'Кол-во комнат до', 'ROOMS_TO', 'Комнат до 1', 'ACTIVE', 'query[data][live.rooms][to]=1', 1);
INSERT INTO crawler_parameters (id, created_date, modified_date, modifier_name, description, key, name, status, value, crawler_id) VALUES (6, '2016-04-27 17:16:43.267626', '2016-04-27 17:16:43.267626', 'artem.demidovich', 'Кол-во объявлений за раз', 'LIMIT', 'Лимит 25 за раз', 'ACTIVE', '25', 2);
INSERT INTO crawler_parameters (id, created_date, modified_date, modifier_name, description, key, name, status, value, crawler_id) VALUES (7, '2016-04-27 17:24:26.926772', '2016-04-27 17:24:26.926772', 'artem.demidovich', 'Регион объявлений', 'REGION', 'Регион Астана', 'ACTIVE', 'query[data][map.geo_id]=105', 2);
INSERT INTO crawler_parameters (id, created_date, modified_date, modifier_name, description, key, name, status, value, crawler_id) VALUES (9, '2016-04-27 17:30:08.282449', '2016-04-27 17:30:08.282449', 'artem.demidovich', 'Кол-во комнат от', 'ROOMS_FROM', 'Комнат от 2', 'ACTIVE', 'query[data][live.rooms][from]=2', 2);
INSERT INTO crawler_parameters (id, created_date, modified_date, modifier_name, description, key, name, status, value, crawler_id) VALUES (10, '2016-04-27 17:31:18.125241', '2016-04-27 17:31:18.125241', 'artem.demidovich', 'Кол-во комнат до', 'ROOMS_TO', 'Комнат до 2', 'ACTIVE', 'query[data][live.rooms][to]=2', 2);
INSERT INTO crawler_parameters (id, created_date, modified_date, modifier_name, description, key, name, status, value, crawler_id) VALUES (11, '2016-04-27 17:16:43.267626', '2016-04-27 17:16:43.267626', 'artem.demidovich', 'Кол-во объявлений за раз', 'LIMIT', 'Лимит 25 за раз', 'ACTIVE', '25', 3);
INSERT INTO crawler_parameters (id, created_date, modified_date, modifier_name, description, key, name, status, value, crawler_id) VALUES (12, '2016-04-27 17:24:26.926772', '2016-04-27 17:24:26.926772', 'artem.demidovich', 'Регион объявлений', 'REGION', 'Регион Астана', 'ACTIVE', 'query[data][map.geo_id]=105', 3);
INSERT INTO crawler_parameters (id, created_date, modified_date, modifier_name, description, key, name, status, value, crawler_id) VALUES (14, '2016-04-27 17:30:08.282449', '2016-04-27 17:30:08.282449', 'artem.demidovich', 'Кол-во комнат от', 'ROOMS_FROM', 'Комнат от 3', 'ACTIVE', 'query[data][live.rooms][from]=3', 3);
INSERT INTO crawler_parameters (id, created_date, modified_date, modifier_name, description, key, name, status, value, crawler_id) VALUES (15, '2016-04-27 17:31:18.125241', '2016-04-27 17:31:18.125241', 'artem.demidovich', 'Кол-во комнат до', 'ROOMS_TO', 'Комнат до 3', 'ACTIVE', 'query[data][live.rooms][to]=3', 3);
INSERT INTO crawler_parameters (id, created_date, modified_date, modifier_name, description, key, name, status, value, crawler_id) VALUES (3, '2016-04-27 17:25:26.449066', '2016-04-27 17:25:26.449066', 'artem.demidovich', 'Категория продажа квартир', 'CATEGORY', 'Продажа квартир', 'ACTIVE', '1', 1);
INSERT INTO crawler_parameters (id, created_date, modified_date, modifier_name, description, key, name, status, value, crawler_id) VALUES (1, '2016-04-27 17:16:43.267626', '2016-04-27 17:16:43.267626', 'artem.demidovich', 'Кол-во объявлений за раз', 'LIMIT', 'Лимит 25 за раз', 'ACTIVE', '25', 1);
INSERT INTO crawler_parameters (id, created_date, modified_date, modifier_name, description, key, name, status, value, crawler_id) VALUES (8, '2016-04-27 17:25:26.449066', '2016-04-27 17:25:26.449066', 'artem.demidovich', 'Категория продажа квартир', 'CATEGORY', 'Продажа квартир', 'ACTIVE', '1', 2);
INSERT INTO crawler_parameters (id, created_date, modified_date, modifier_name, description, key, name, status, value, crawler_id) VALUES (13, '2016-04-27 17:25:26.449066', '2016-04-27 17:25:26.449066', 'artem.demidovich', 'Категория продажа квартир', 'CATEGORY', 'Продажа квартир', 'ACTIVE', '1', 3);
INSERT INTO crawler_parameters (id, created_date, modified_date, modifier_name, description, key, name, status, value, crawler_id) VALUES (16, '2016-05-07 02:21:47.150391', '2016-05-07 02:21:47.150391', 'artem.demidovich', 'Кол-во объявлений за раз', 'LIMIT', 'Кол-во объявлений за раз', 'ACTIVE', '25', 4);
INSERT INTO crawler_parameters (id, created_date, modified_date, modifier_name, description, key, name, status, value, crawler_id) VALUES (17, '2016-04-27 17:25:26.449066', '2016-04-27 17:25:26.449066', 'artem.demidovich', 'Категория продажа квартир', 'CATEGORY', 'Продажа квартир', 'ACTIVE', '1', 4);
INSERT INTO crawler_parameters (id, created_date, modified_date, modifier_name, description, key, name, status, value, crawler_id) VALUES (18, '2016-05-07 21:55:45.320654', '2016-05-07 21:55:45.320654', 'artem.demidovich', 'Категория аренда квартир', 'CATEGORY', 'Аренда квартир', 'ACTIVE', '2', 5);
INSERT INTO crawler_parameters (id, created_date, modified_date, modifier_name, description, key, name, status, value, crawler_id) VALUES (19, '2016-05-07 21:56:16.458015', '2016-05-07 21:56:16.458015', 'artem.demidovich', 'Кол-во объявлений за раз', 'LIMIT', 'Кол-во объявлений за раз', 'ACTIVE', '25', 5);


--
-- TOC entry 2394 (class 0 OID 0)
-- Dependencies: 178
-- Name: crawler_parameters_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('crawler_parameters_seq', 1, false);


--
-- TOC entry 2365 (class 0 OID 86891)
-- Dependencies: 175
-- Data for Name: crawler_proxies; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO crawler_proxies (id, created_date, modified_date, modifier_name, auth_type, description, host, inactive_from, last_check, last_usage, login, name, password, port, status, usage_count, type) VALUES (1, '2016-04-27 18:53:33.797039', '2016-04-27 18:53:33.797039', 'artem.demidovich', 'NONE', NULL, '5.135.254.35', NULL, NULL, NULL, NULL, 'France Free server', NULL, 3128, 'ACTIVE', 0, 'HTTPS');


--
-- TOC entry 2395 (class 0 OID 0)
-- Dependencies: 179
-- Name: crawler_proxies_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('crawler_proxies_seq', 1, false);


--
-- TOC entry 2396 (class 0 OID 0)
-- Dependencies: 180
-- Name: crawler_user_agent_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('crawler_user_agent_seq', 1, false);


--
-- TOC entry 2366 (class 0 OID 86899)
-- Dependencies: 176
-- Data for Name: crawler_user_agents; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO crawler_user_agents (id, created_date, modified_date, modifier_name, description, name, status, user_agent, type) VALUES (2, '2016-04-27 18:48:36.468263', '2016-04-27 18:48:36.468263', 'artem.demidovich', NULL, 'Safary', 'ACTIVE', 'Mozilla/5.0 (iPad; CPU OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A5355d Safari/8536.25', 'MOBILE_PLATFORM');
INSERT INTO crawler_user_agents (id, created_date, modified_date, modifier_name, description, name, status, user_agent, type) VALUES (1, '2016-04-27 18:47:40.426196', '2016-04-27 18:47:40.426196', 'artem.demidovich', NULL, 'Android', 'ACTIVE', 'Mozilla/5.0 (Linux; Android 4.0.4; Galaxy Nexus Build/IMM76B) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.133 Mobile Safari/535.19', 'MOBILE_PLATFORM');
INSERT INTO crawler_user_agents (id, created_date, modified_date, modifier_name, description, name, status, user_agent, type) VALUES (3, '2016-04-27 18:49:32.702703', '2016-04-27 18:49:32.702703', 'artem.demidovich', NULL, 'Windows Phone', 'ACTIVE', 'Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; AS; rv:11.0) like Gecko', 'MOBILE_PLATFORM');


--
-- TOC entry 2397 (class 0 OID 0)
-- Dependencies: 181
-- Name: crawlers_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('crawlers_seq', 1, false);


--
-- TOC entry 2374 (class 0 OID 87069)
-- Dependencies: 184
-- Data for Name: region; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1751, 52.9971008, 63.1228981, 'Горняцкий', 247, 'gornyaczkij', '1229', '3', '1230', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (977, 53.4705009, 66.6824036, 'Новоишимское', 264, 'novoishimskoe', '1415', '3', '1416', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34333, 46.854599, 3.66494989, 'Дивон-ле-Бен', 33547, 'divon-le-ben', '2240', '2', '2241', 'CITY', '5');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34528, NULL, NULL, 'Илийский', 2, NULL, NULL, '4', NULL, 'DISTRICT', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34529, NULL, NULL, 'Карасайский', 2, NULL, NULL, '4', NULL, 'DISTRICT', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34530, NULL, NULL, 'Майкудук', 239, NULL, NULL, '4', NULL, 'DISTRICT', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34531, NULL, NULL, 'Михайловка', 239, NULL, NULL, '4', NULL, 'DISTRICT', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34532, NULL, NULL, 'Пришахтинск', 239, NULL, NULL, '4', NULL, 'DISTRICT', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34533, NULL, NULL, 'р-н ЖБИ', 239, NULL, NULL, '4', NULL, 'DISTRICT', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33534, NULL, NULL, 'р-н Нового рынка', 239, NULL, NULL, NULL, NULL, 'DISTRICT', NULL);
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33536, NULL, NULL, 'Юго-Восток', 239, NULL, NULL, NULL, NULL, 'DISTRICT', NULL);
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33538, NULL, NULL, 'Федоровка', 239, NULL, NULL, NULL, NULL, 'DISTRICT', NULL);
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34201, 34.9477005, 97.3187027, 'Китай', NULL, 'kitaj', '2059', '1', '2062', 'COUNTRY', '4');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (2000, 0, 0, 'Капшагай', 132, NULL, NULL, NULL, NULL, 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (2001, 0, 0, 'Атасу', 235, NULL, NULL, NULL, NULL, 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (2002, 48.4630013, 58.0429993, 'Жем', 124, NULL, NULL, NULL, NULL, 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (2003, 49.3518982, 81.0242996, 'Чарск', 216, NULL, NULL, NULL, NULL, 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (2, 43.2859001, 76.9129028, 'Алматы', 1, 'almaty', '2', '2', '305', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (3, 43.2812996, 76.8519974, 'Алатауский р-н', 2, 'almaty-alatauskij', '3', '3', '62', 'DISTRICT', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (4, 43.2994003, 76.8432007, 'мкр 6-й градокомплекс', 3, 'almaty-alatauskij-mkr-6-gradokompleks', '14', '4', '15', 'MICRODISTRICT', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (5, 43.3158989, 76.8162003, 'мкр АДК', 3, 'almaty-alatauskij-mkr-adk', '16', '4', '17', 'MICRODISTRICT', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (6, 43.280899, 76.8462982, 'мкр Айгерим-1', 3, 'almaty-alatauskij-mkr-ajgerim-1', '18', '4', '19', 'MICRODISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (7, 43.2700996, 76.8405991, 'мкр Айгерим-2', 3, 'almaty-alatauskij-mkr-ajgerim-2', '20', '4', '21', 'MICRODISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (8, 43.2569008, 76.8314972, 'мкр Акбулак', 3, 'almaty-alatauskij-mkr-akbulak', '22', '4', '23', 'MICRODISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (9, 43.2565994, 76.7958984, 'мкр Алгабас', 3, 'almaty-alatauskij-mkr-algabas', '26', '4', '27', 'MICRODISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (10, 43.3097992, 76.8834991, 'мкр Байбесик', 3, 'almaty-alatauskij-mkr-bajbesik', '28', '4', '29', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (11, 43.3134003, 76.8759003, 'мкр Дархан', 3, 'almaty-alatauskij-mkr-darhan', '30', '4', '31', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (12, 43.2872009, 76.8831024, 'мкр Заря Востока', 3, 'almaty-alatauskij-mkr-zarja-vostoka', '32', '4', '33', 'MICRODISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (13, 43.294899, 76.8406982, 'мкр Коккайнар', 3, 'almaty-alatauskij-mkr-kokkajnar', '36', '4', '37', 'MICRODISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (14, 43.3293991, 76.8481979, 'мкр Красный трудовик', 3, 'almaty-alatauskij-mkr-krasnyj-trudovik', '38', '4', '39', 'MICRODISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1, 48.5481987, 67.6274033, 'Казахстан', NULL, NULL, '1', '1', '1506', 'COUNTRY', '4');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (15, 43.2677994, 76.8618011, 'мкр Курылысшы', 3, 'almaty-alatauskij-mkr-kurylysshy', '40', '4', '41', 'MICRODISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (16, 43.3226013, 76.8880997, 'мкр Ожет', 3, 'almaty-alatauskij-mkr-ozhet', '42', '4', '43', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (17, 43.2762985, 76.8580017, 'мкр Туркестан', 3, 'almaty-alatauskij-mkr-turkestan', '44', '4', '45', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (18, 43.3022003, 76.873497, 'мкр Улжан-1', 3, 'almaty-alatauskij-mkr-ulzhan-1', '46', '4', '47', 'MICRODISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (19, 43.3042984, 76.8831024, 'мкр Улжан-2', 3, 'almaty-alatauskij-mkr-ulzhan-2', '48', '4', '49', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (20, 43.2952995, 76.8606033, 'мкр Шанырак-1', 3, 'almaty-alatauskij-mkr-shanyrak-1', '50', '4', '51', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (21, 43.2910004, 76.8479004, 'мкр Шанырак-2', 3, 'almaty-alatauskij-mkr-shanyrak-2', '52', '4', '53', 'MICRODISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (22, 43.2748985, 76.847702, 'мкр Шанырак-3', 3, 'almaty-alatauskij-mkr-shanyrak-3', '54', '4', '55', 'MICRODISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (23, 43.2747993, 76.8303986, 'мкр Шанырак-4', 3, 'almaty-alatauskij-mkr-shanyrak-4', '56', '4', '57', 'MICRODISTRICT', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (24, 43.3204002, 76.8415985, 'мкр Шанырак-5', 3, 'almaty-alatauskij-mkr-shanyrak-5', '58', '4', '59', 'MICRODISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (25, 43.3286018, 76.8961029, 'мкр Шанырак-6', 3, 'almaty-alatauskij-mkr-shanyrak-6', '60', '4', '61', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (26, 43.2611008, 76.8945007, 'Алмалинский р-н', 2, 'almaty-almalinskij', '63', '3', '68', 'DISTRICT', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (27, 43.2523994, 76.8691025, 'мкр Тастак-2', 26, 'almaty-almalinskijj-tastak-2', '64', '4', '65', 'MICRODISTRICT', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (28, 43.2559013, 76.8833008, 'мкр Тастак-3', 26, 'almaty-almalinskij-tastak-3', '66', '4', '67', 'MICRODISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (29, 43.2408981, 76.8389969, 'Ауэзовский р-н', 2, 'almaty-aujezovskij', '69', '3', '162', 'DISTRICT', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (30, 43.2308006, 76.8488998, 'мкр №1', 29, 'almaty-aujezovskij-mkr-1', '70', '4', '71', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (31, 43.2170982, 76.8699036, 'мкр №10', 29, 'almaty-aujezovskij-mkr-10', '72', '4', '73', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (32, 43.2160988, 76.8593979, 'мкр №10 А', 29, 'almaty-aujezovskij-mkr-10a', '74', '4', '75', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33, 43.2221985, 76.8735962, 'мкр №11', 29, 'almaty-aujezovskij-mkr-11', '76', '4', '77', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34, 43.2204018, 76.8643036, 'мкр №12', 29, 'almaty-aujezovskij-mkr-12', '78', '4', '79', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (35, 43.2344017, 76.8638992, 'мкр №2', 29, 'almaty-aujezovskij-mkr-2', '80', '4', '81', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (36, 43.2240982, 76.851799, 'мкр №3', 29, 'almaty-aujezovskij-mkr-3', '82', '4', '83', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (37, 43.2274017, 76.8585968, 'мкр №4', 29, 'almaty-aujezovskij-mkr-4', '84', '4', '85', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (38, 43.2313004, 76.8638, 'мкр №5', 29, 'almaty-aujezovskij-mkr-5', '86', '4', '87', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (39, 43.2201996, 76.8576965, 'мкр №6', 29, 'almaty-aujezovskij-mkr-6', '88', '4', '89', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (40, 43.2243996, 76.8622971, 'мкр №7', 29, 'almaty-aujezovskij-mkr-7', '90', '4', '91', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (41, 43.2254982, 76.8691025, 'мкр №8', 29, 'almaty-aujezovskij-mkr-8', '92', '4', '93', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (42, 43.2123985, 76.8638992, 'мкр №9', 29, 'almaty-aujezovskij-mkr-9', '94', '4', '95', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (43, 43.2421989, 76.8330002, 'мкр Аксай-1', 29, 'almaty-aujezovskij-mkr-aksay-1', '98', '4', '99', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (44, 43.2406998, 76.8256989, 'мкр Аксай-1А', 29, 'almaty-aujezovskij-mkr-aksay-1a', '100', '4', '101', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (45, 43.2369995, 76.8354034, 'мкр Аксай-2', 29, 'almaty-aujezovskij-mkr-aksay-2', '102', '4', '103', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (46, 43.2327003, 76.8376007, 'мкр Аксай-2А', 29, 'almaty-aujezovskij-mkr-aksay-2a', '104', '4', '105', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (47, 43.2354012, 76.8302002, 'мкр Аксай-3', 29, 'almaty-aujezovskij-mkr-aksay-3', '106', '4', '107', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (48, 43.2358017, 76.8265991, 'мкр Аксай-3А', 29, 'almaty-aujezovskij-mkr-aksay-3a', '108', '4', '109', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (49, 43.2346001, 76.8216019, 'мкр Аксай-3Б', 29, 'almaty-aujezovskij-mkr-aksay-3b', '110', '4', '111', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (50, 43.2277985, 76.8394012, 'мкр Аксай-4', 29, 'almaty-aujezovskij-mkr-aksay-4', '112', '4', '113', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (51, 43.2272987, 76.8336029, 'мкр Аксай-5', 29, 'almaty-aujezovskij-mkr-aksay-5', '114', '4', '115', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (52, 43.2335014, 76.8162994, 'мкр Алтын Бесик', 29, 'almaty-aujezovskij-mkr-altyn-besik', '116', '4', '117', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (53, 43.2108994, 76.8531036, 'мкр Астана', 29, 'almaty-aujezovskij-mkr-astana', '118', '4', '119', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (54, 43.2389984, 76.8199005, 'мкр Баянаул', 29, 'almaty-aujezovskij-mkr-bajanaul', '120', '4', '121', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33511, 41.3793983, 74.1531982, 'Кыргызстан', NULL, 'kyrgyzstan', '1531', '1', '1540', 'COUNTRY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (55, 43.2215996, 76.8299026, 'мкр Достык', 29, 'almaty-aujezovskij-mkr-dostyk', '122', '4', '123', 'MICRODISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (56, 43.1963005, 76.8921967, 'мкр Дубок', 29, 'almaty-aujezovskij-mkr-dubok', '124', '4', '125', 'MICRODISTRICT', '17');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (57, 43.1964989, 76.908699, 'мкр Дубок-2', 29, 'almaty-aujezovskij-mkr-dubok-2', '126', '4', '127', 'MICRODISTRICT', '17');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (58, 43.2036018, 76.8451996, 'мкр Таугуль-2', 29, 'almaty-aujezovskij-mkr-taugul-2', '156', '4', '157', 'MICRODISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (59, 43.2218018, 76.8406982, 'мкр Жетысу-1', 29, 'almaty-aujezovskij-mkr-zhetysu-1', '128', '4', '129', 'MICRODISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (60, 43.2219009, 76.8407974, 'мкр Жетысу-2', 29, 'almaty-aujezovskij-mkr-zhetysu-2', '130', '4', '131', 'MICRODISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (61, 43.2212982, 76.8423996, 'мкр Жетысу-3', 29, 'almaty-aujezovskij-mkr-zhetysu-3', '132', '4', '133', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (62, 43.2220993, 76.8367996, 'мкр Жетысу-4', 29, 'almaty-aujezovskij-mkr-zhetysu-4', '134', '4', '135', 'MICRODISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (65, 43.2126007, 76.8458023, 'мкр Мамыр', 29, 'almaty-aujezovskij-mkr-mamyr', '136', '4', '137', 'MICRODISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (66, 43.2126007, 76.8458023, 'мкр Мамыр-7', 29, 'almaty-aujezovskij-mkr-mamyr-7', '146', '4', '147', 'MICRODISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (67, 43.2389984, 76.8797989, 'мкр Сайран', 29, 'almaty-aujezovskij-mkr-sayran', '148', '4', '149', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (69, 43.2131996, 76.8798981, 'мкр Таугуль', 29, 'almaty-aujezovskij-mkr-taugul', '152', '4', '153', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (70, 43.2005005, 76.8551025, 'мкр Таугуль-1', 29, 'almaty-aujezovskij-mkr-taugul-1', '154', '4', '155', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (71, 43.2002983, 76.8563995, 'мкр Таугуль-3', 29, 'almaty-aujezovskij-mkr-taugul-3', '158', '4', '159', 'MICRODISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (72, 43.2061005, 76.8404999, 'мкр Школьный', 29, 'almaty-aujezovskij-mkr-shkolnyj', '160', '4', '161', 'MICRODISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (73, 43.2134018, 76.8880005, 'Бостандыкский р-н', 2, 'almaty-bostandykskij', '163', '3', '200', 'DISTRICT', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (74, 43.2043991, 76.9012985, 'мкр Алмагуль', 73, 'almaty-bostandykskij-almagul', '176', '4', '177', 'MICRODISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (75, 43.1986008, 76.9134979, 'мкр Баганашыл', 73, 'almaty-bostandykskij-baganashyl', '178', '4', '179', 'MICRODISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (76, 43.1955986, 76.9033966, 'мкр Казахфильм', 73, 'almaty-bostandykskij-kazahfilm', '180', '4', '181', 'MICRODISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (77, 43.2284012, 76.927597, 'мкр Коктем-1', 73, 'almaty-bostandykskij-koktem-1', '182', '4', '183', 'MICRODISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (78, 43.2289009, 76.9160995, 'мкр Коктем-2', 73, 'almaty-bostandykskij-koktem-2', '184', '4', '185', 'MICRODISTRICT', '17');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (79, 43.2349014, 76.9135971, 'мкр Коктем-3', 73, 'almaty-bostandykskij-koktem-3', '186', '4', '187', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (80, 43.2011986, 76.8830032, 'мкр Орбита-1', 73, 'almaty-bostandykskij-orbita-1', '192', '4', '193', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (81, 43.1972008, 76.8845978, 'мкр Орбита-2', 73, 'almaty-bostandykskij-orbita-2', '194', '4', '195', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (82, 43.2000999, 76.8755035, 'мкр Орбита-3', 73, 'almaty-bostandykskij-orbita-3', '196', '4', '197', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (83, 43.3011017, 76.8983002, 'Жетысуский р-н', 2, 'almaty-zhetysuskij', '201', '3', '218', 'DISTRICT', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (84, 43.3209991, 76.9097977, 'мкр Айнабулак-1', 83, 'almaty-zhetysuskij-ajnabulak-1', '204', '4', '205', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (85, 43.3165016, 76.9140015, 'мкр Айнабулак-2', 83, 'almaty-zhetysuskij-ajnabulak-2', '206', '4', '207', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (86, 43.3235016, 76.9200974, 'мкр Айнабулак-3', 83, 'almaty-zhetysuskij-ajnabulak-3', '208', '4', '209', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (162, 43.6822014, 77.1227036, 'Жетыген', 132, 'zhetygen', '683', '3', '684', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (88, 43.3041992, 76.9235992, 'мкр Кулагер', 83, 'almaty-zhetysuskij-kulager', '214', '4', '215', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (89, 43.2282982, 76.9586029, 'Медеуский р-н', 2, 'almaty-medeuskij', '219', '3', '256', 'DISTRICT', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (90, 43.2938995, 76.9860001, 'мкр Атырау', 89, 'almaty-medeuskij-atyrau', '232', '4', '233', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (91, 43.1907997, 77.0289993, 'мкр Бутаковка', 89, 'almaty-medeuskij-butakovka', '234', '4', '235', 'MICRODISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (92, 43.2165985, 76.9496994, 'мкр Горный Гигант', 89, 'almaty-medeuskij-gornyj-gigant', '236', '4', '237', 'MICRODISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (93, 43.2793007, 77.0027008, 'мкр Думан-1', 89, 'almaty-medeuskij-duman-1', '238', '4', '239', 'MICRODISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (94, 43.2182999, 76.972702, 'мкр Коктобе', 89, 'almaty-medeuskij-koktobe', '244', '4', '245', 'MICRODISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (95, 43.2368011, 76.9536972, 'мкр Самал-1', 89, 'almaty-medeuskij-samal-1', '248', '4', '249', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (96, 43.2313995, 76.9546967, 'мкр Самал-2', 89, 'almaty-medeuskij-samal-2', '250', '4', '251', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (97, 43.2256012, 76.956398, 'мкр Самал-3', 89, 'almaty-medeuskij-samal-3', '252', '4', '253', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (98, 43.1991997, 76.9763031, 'мкр Тау Самал', 89, 'almaty-medeuskij-tau-samal', '254', '4', '255', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (99, 43.3459015, 76.9776001, 'Турксибский р-н', 2, 'almaty-turksibskij', '285', '3', '304', 'DISTRICT', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (100, 43.3446007, 76.9872971, 'мкр Алтай-1', 99, 'almaty-turksibskij-altaj-1', '288', '4', '289', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (101, 43.3506012, 76.9898987, 'мкр Алтай-2', 99, 'almaty-turksibskij-altaj-2', '290', '4', '291', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (102, 43.3630981, 76.9950027, 'мкр Жулдыз-1', 99, 'almaty-turksibskij-zhuldyz-1', '292', '4', '293', 'MICRODISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (103, 43.3581009, 76.9929962, 'мкр Жулдыз-2', 99, 'almaty-turksibskij-zhuldyz-2', '294', '4', '295', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (104, 43.3334007, 76.9923019, 'мкр Маяк', 99, 'almaty-turksibskij-majak', '302', '4', '303', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (105, 51.1582985, 71.4313965, 'Астана', 1, 'astana', '306', '2', '317', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (106, 51.1328011, 71.5100021, 'Алматинский р-н', 105, 'astana-almatinskij', '311', '3', '312', 'DISTRICT', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (107, 51.2062988, 71.3869019, 'Сарыаркинский р-н', 105, 'astana-saryarkinskij', '313', '3', '316', 'DISTRICT', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (111, 45.6302986, 63.3022003, 'Байконыр', 1, 'bajkonyr', '318', '2', '319', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (112, 52.1357994, 70.0003967, 'Акмолинская обл.', 1, 'akmolinskaja-oblast', '320', '2', '465', 'REGION', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (113, 51.9948997, 70.9325027, 'Акколь', 112, 'akkol', '323', '3', '324', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (114, 51.8097, 68.3495026, 'Атбасар', 112, 'atbasar', '331', '3', '332', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (115, 53.0821991, 70.310997, 'Боровое', 112, 'borovoe', '337', '3', '338', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (116, 51.1049004, 66.3147964, 'Державинск', 112, 'derzhavinsk', '345', '3', '346', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (117, 51.6248016, 73.1051025, 'Ерейментау', 112, 'erejmentau', '351', '3', '352', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (118, 51.9546013, 66.4057999, 'Есиль', 112, 'esil', '353', '3', '354', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (119, 53.2863007, 69.3945999, 'Кокшетау', 112, 'kokshetau', '387', '3', '388', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (120, 52.6357994, 70.4271011, 'Макинск', 112, 'makinsk', '411', '3', '412', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (121, 52.3466988, 71.8797989, 'Степногорск', 112, 'stepnogorsk', '435', '3', '436', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (122, 52.8372002, 70.7848969, 'Степняк', 112, 'stepnjak', '437', '3', '438', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (123, 52.9322014, 70.1837006, 'Щучинск', 112, 'shhuchinsk', '463', '3', '464', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (124, 49.1890984, 57.289299, 'Актюбинская обл.', 1, 'aktjubinskaja-oblast', '466', '2', '529', 'REGION', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (125, 50.2827988, 57.1907997, 'Актобе', 124, 'aktobe', '471', '3', '478', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (126, 49.8951988, 57.3274002, 'Алга', 124, 'alga', '481', '3', '482', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (127, 49.4625015, 57.4185982, 'Кандыагаш', 124, 'kandyagash', '489', '3', '490', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (128, 49.1453018, 57.1268997, 'Темир', 124, 'temir', '517', '3', '518', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (129, 50.2542992, 58.4459991, 'Хромтау', 124, 'hromtau', '519', '3', '520', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (130, 47.8339005, 59.6162987, 'Шалкар', 124, 'shalkar', '521', '3', '522', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (131, 48.8210983, 58.1472015, 'Эмба', 124, 'jemba', '525', '3', '526', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (132, 45.2150993, 77.3393021, 'Алматинская обл.', 1, 'almatinskaja-oblast', '530', '2', '909', 'REGION', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (133, 43.2112007, 76.7628021, 'Абай', 132, 'almatinskaja-oblast-abaj', '541', '3', '542', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (134, 43.3465004, 77.2646027, 'Азат', 132, 'azat', '551', '3', '552', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (136, 43.5008011, 76.2742996, 'Аксенгир', 132, 'aksengir', '561', '3', '562', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (139, 43.4003983, 77.5250015, 'Алмалы', 132, 'almaly', '571', '3', '572', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (140, 43.2192993, 76.6821976, 'Алмалыбак (КИЗ)', 132, 'almalybak-kiz', '575', '3', '576', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (142, 43.4123001, 76.9132004, 'Ащибулак', 132, 'ashhibulak', '585', '3', '588', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (143, 43.1917992, 76.910202, 'Баганашыл', 132, 'baganashyl', '591', '3', '592', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (144, 43.4911003, 77.0512009, 'Байсерке', 132, 'bajserke', '597', '3', '598', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (145, 43.4033012, 77.2247009, 'Байтерек (Новоалексеевка)', 132, 'bajterek-novoalekseevka', '599', '3', '600', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (146, 44.8111992, 76.2705002, 'Баканас', 132, 'bakanas', '601', '3', '602', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (147, 43.5047989, 77.5422974, 'Балтабай', 132, 'baltabaj', '607', '3', '608', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (148, 43.3158989, 77.1007004, 'Бельбулак (Мичурино)', 132, 'belbulak-michurino', '615', '3', '616', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (149, 43.4972992, 77.5201035, 'Бирлик', 132, 'birlik', '619', '3', '620', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (150, 43.3013992, 77.0406036, 'Бесагаш (Дзержинское)', 132, 'besagash-dzerzhinskoe', '621', '3', '622', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (151, 43.2200012, 77.0995026, 'Бескайнар (Горный Садовод)', 132, 'beskajnar-gornyj-sadovod', '623', '3', '624', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (152, 43.1904984, 76.8327026, 'Верхняя Каменка', 132, 'verhnjaja-kamenka', '625', '3', '626', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (153, 43.3577003, 76.8574982, 'Боралдай (Бурундай)', 132, 'boroldaj-burundaj', '629', '3', '630', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (154, 43.3493996, 77.0537033, 'Гулдала', 132, 'guldala', '633', '3', '634', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (156, 43.3460999, 77.4708023, 'Есик', 132, 'esik', '539', '3', '540', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (157, 43.535099, 77.1511002, 'Жалкамыс', 132, 'zhalkamys', '655', '3', '656', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (158, 43.1817017, 76.7822037, 'Жанатурмыс', 132, 'zhanaturmys', '669', '3', '670', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (159, 43.1660004, 76.5662003, 'Жандосов', 132, 'zhandosov', '675', '3', '676', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (160, 44.1661987, 80.0037994, 'Жаркент', 132, 'zharkent', '679', '3', '680', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (161, 43.3759995, 76.8034973, 'Жармухамбет', 132, 'zharmuhambet', '681', '3', '682', 'CITY', '17');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (163, 50.1579018, 71.4459, 'Заречное', 132, 'zarechnoe', '689', '3', '690', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (164, 43.2336998, 76.7701035, 'Иргели', 132, 'irgeli', '695', '3', '696', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (165, 43.3507996, 77.1404037, 'ИЯФ', 132, 'ijaf', '697', '3', '698', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (166, 43.3815994, 76.8503036, 'КазЦИК', 132, 'kazcik', '699', '3', '700', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (168, 43.8634987, 77.0653, 'Капчагай', 132, 'kapchagaj', '537', '3', '538', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (169, 43.3977013, 77.151001, 'Карабулак (п.Ключи)', 132, 'karabulak', '709', '3', '710', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (171, 43.1654015, 76.4097977, 'Каргалы (п. Фабричный)', 132, 'kargaly-fabrichnyj', '725', '3', '726', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (172, 43.2028008, 76.6276016, 'Каскелен', 132, 'kaskelen', '535', '3', '536', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (173, 43.2364998, 76.6648026, 'Кемертоган', 132, 'kemertogan', '729', '3', '730', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (174, 43.2501984, 76.7583008, 'Кок-Лай-Сай', 132, 'kok-laj-saj', '741', '3', '742', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (175, 47.1436005, 81.892601, 'Кокозек', 132, 'kokozek', '745', '3', '746', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (178, 43.3941994, 76.9371033, 'Коянкоз', 132, 'kojankoz', '765', '3', '766', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (179, 43.3404999, 76.6617966, 'Кошмамбет', 132, 'koshmambet', '769', '3', '770', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (180, 43.1721992, 76.7664032, 'Кыргауылды', 132, 'kyrgauyldy', '789', '3', '790', 'CITY', '17');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (181, 43.3419991, 76.9385986, 'М. Туймебаев', 132, 'm-tujmebaev', '793', '3', '794', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (183, 43.3320007, 76.7016983, 'Мерей (Селекция)', 132, 'merej-selekcija', '797', '3', '798', 'CITY', '17');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (184, 43.6875992, 77.1930008, 'Нура', 132, 'nura', '811', '3', '812', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (185, 43.421299, 77.0328979, 'Отеген батыр', 132, 'otegen-batyr', '815', '3', '816', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (186, 43.3891983, 77.1147995, 'Панфилова (Табаксовхоз)', 132, 'panfilova-tabaksovhoz', '819', '3', '820', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (188, 43.2033997, 76.7436981, 'Райымбек', 132, 'rajymbek', '821', '3', '822', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (189, 43.4081001, 77.0089035, 'Рахат (Покровка)', 132, 'rahat-pokrovka', '823', '3', '824', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (192, 43.4720993, 76.117897, 'Самсы', 132, 'samsy', '829', '3', '830', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (193, 45.4132004, 79.917099, 'Сарканд', 132, 'sarkand', '835', '3', '836', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (194, 43.3092003, 77.2417984, 'Талгар', 132, 'talgar', '841', '3', '842', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (195, 45.0140991, 78.3640976, 'Талдыкорган', 132, 'taldykorgan', '533', '3', '534', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (197, 44.8633995, 78.7650986, 'Текели', 132, 'tekeli', '847', '3', '848', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (199, 43.3055992, 77.0653992, 'Туздыбастау (Калинино)', 132, 'tuzdybastau-kalinino', '863', '3', '864', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (200, 43.3166008, 76.5856018, 'Турар', 132, 'turar', '867', '3', '868', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (201, 43.3941994, 77.5887985, 'Турген', 132, 'turgen', '869', '3', '870', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (202, 43.4306984, 70.7739029, 'Ушарал', 132, 'usharal', '883', '3', '884', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (203, 45.2514992, 77.9769974, 'Уштобе', 132, 'ushtobe', '887', '3', '888', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (204, 43.5960999, 78.2498016, 'Чилик', 132, 'chilik', '893', '3', '894', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (205, 43.5383987, 79.4630966, 'Чунджа', 132, 'chundzha', '895', '3', '896', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (206, 45.8083992, 77.1756973, 'Каратал', 132, 'karatalskij-rayon', '723', '3', '724', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (207, 43.1893005, 76.5354996, 'Ушконыр', 132, 'shamalgan', '885', '3', '886', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (211, 43.3721008, 76.6272964, 'Станция Шамалган', 132, 'stancija-shamalgan', '839', '3', '840', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (212, 44.4005013, 78.5101013, 'Жаналык', 132, 'zhanalyk', '667', '3', '668', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (213, 47.3865013, 52.5798988, 'Атырауская обл.', 1, 'atyrauskaja-oblast', '910', '2', '949', 'REGION', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (214, 47.1013985, 51.9096985, 'Атырау', 213, 'atyrau', '911', '3', '912', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (215, 46.9602013, 53.9980011, 'Кульсары', 213, 'kulsary', '937', '3', '938', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (216, 48.8246994, 81.2285004, 'Восточно-Казахстанская обл.', 1, 'vostochno-kazahstanskaja-oblast', '950', '2', '1041', 'REGION', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (217, 47.5560989, 80.4921036, 'Аягоз', 216, 'ajagoz', '961', '3', '962', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (218, 47.5747986, 84.2425003, 'Зайсан', 216, 'zajsan', '981', '3', '982', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (219, 49.7401009, 83.3087997, 'Зыряновск', 216, 'zyrjanovsk', '983', '3', '984', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (220, 50.6253014, 79.1159973, 'Курчатов', 216, 'kurchatov', '995', '3', '996', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (221, 50.1973991, 83.1443024, 'Риддер', 216, 'ridder', '1015', '3', '1016', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (222, 50.4328995, 80.2565994, 'Семей', 216, 'semej', '1017', '3', '1020', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (223, 49.7840004, 82.9235992, 'Серебрянск', 216, 'serebrjansk', '1021', '3', '1022', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (224, 49.9524002, 82.6016998, 'Усть-Каменогорск', 216, 'ust-kamenogorsk', '951', '3', '954', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (225, 49.6680984, 81.7302017, 'Шар', 216, 'shar', '1035', '3', '1036', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (226, 50.4342995, 81.9457016, 'Шемонаиха', 216, 'shemonaiha', '1037', '3', '1038', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (227, 44.2557983, 72.0988007, 'Жамбылская обл.', 1, 'zhambylskaja-oblast', '1042', '2', '1089', 'REGION', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (228, 43.5634003, 69.7357025, 'Жанатас', 227, 'zhanatas', '1057', '3', '1058', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (229, 43.1819, 70.4598999, 'Каратау', 227, 'karatau', '1061', '3', '1062', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (230, 42.8916016, 71.3921967, 'Тараз', 227, 'taraz', '1075', '3', '1076', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (231, 43.6054001, 73.7612991, 'Шу', 227, 'shu', '1083', '3', '1084', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (232, 49.9889984, 50.5107002, 'Западно-Казахстанская обл.', 1, 'zapadno-kazahstanskaja-oblast', '1090', '2', '1133', 'REGION', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (233, 51.1674995, 53.0149002, 'Аксай', 232, 'aksaj', '1091', '3', '1092', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (234, 51.2302017, 51.4310989, 'Уральск', 232, 'uralsk', '1127', '3', '1128', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (235, 49.5733986, 73.7798004, 'Карагандинская обл.', 1, 'karagandinskaja-oblast', '1134', '2', '1211', 'REGION', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (236, 49.6323013, 72.8673019, 'Абай', 235, 'karagandinskaja-oblast-abaj', '1143', '3', '1144', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (237, 46.8440018, 74.9748993, 'Балхаш', 235, 'balhash', '1151', '3', '1152', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (238, 47.7890015, 67.714798, 'Жезказган', 235, 'zhezkazgan', '1159', '3', '1160', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (239, 49.7845001, 73.0979996, 'Караганда', 235, 'karaganda', '1137', '3', '1142', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (240, 48.0108986, 70.7949982, 'Каражал', 235, 'karazhal', '1161', '3', '1162', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (241, 49.4105988, 75.4725037, 'Каркаралинск', 235, 'karkaralinsk', '1163', '3', '1164', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (242, 46.0332985, 73.6929016, 'Приозёрск', 235, 'priozjorsk', '1181', '3', '1182', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (243, 49.7981987, 72.8451004, 'Сарань', 235, 'saran', '1185', '3', '1186', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (244, 47.9023018, 67.5317993, 'Сатпаев', 235, 'satpaev', '1189', '3', '1190', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (245, 50.0634995, 72.9589005, 'Темиртау', 235, 'temirtau', '1193', '3', '1194', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (246, 49.7088013, 72.5914001, 'Шахтинск', 235, 'shahtinsk', '1205', '3', '1206', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (247, 51.4696999, 63.9799004, 'Костанайская обл.', 1, 'kostanajskaja-oblast', '1212', '2', '1289', 'REGION', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (248, 50.2560005, 66.9052963, 'Аркалык', 247, 'arkalyk', '1219', '3', '1220', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (249, 52.1633987, 61.2487984, 'Житикара', 247, 'zhitikara', '1237', '3', '1238', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (250, 53.2072983, 63.6747017, 'Костанай', 247, 'kostanaj', '1249', '3', '1250', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (251, 52.5661011, 62.5643997, 'Лисаковск', 247, 'lisakovsk', '1257', '3', '1258', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (252, 52.9756012, 63.1236992, 'Рудный', 247, 'rudnyj', '1269', '3', '1270', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (253, 45.3424988, 63.2988014, 'Кызылординская обл.', 1, 'kyzylordinskaja-oblast', '1290', '2', '1309', 'REGION', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (254, 46.7994003, 61.6749001, 'Аральск', 253, 'aralsk', '1291', '3', '1292', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (255, 45.7612991, 62.1044006, 'Казалинск', 253, 'kazalinsk', '1299', '3', '1300', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (256, 44.8507996, 65.5033035, 'Кызылорда', 253, 'kyzylorda', '1303', '3', '1304', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (257, 44.264801, 53.7626991, 'Мангистауская обл.', 1, 'mangistauskaja-oblast', '1310', '2', '1351', 'REGION', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (258, 43.6464996, 51.1721992, 'Актау', 257, 'aktau', '1311', '3', '1312', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (259, 43.3342018, 52.8712997, 'Жанаозен', 257, 'zhanaozen', '1329', '3', '1330', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (260, 52.0808983, 76.7461014, 'Павлодарская обл.', 1, 'pavlodarskaja-oblast', '1352', '2', '1397', 'REGION', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (261, 52.0382004, 76.9292984, 'Аксу', 260, 'aksu', '1355', '3', '1356', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (262, 52.2942009, 76.9717026, 'Павлодар', 260, 'pavlodar', '1353', '3', '1354', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (263, 51.7294998, 75.3299026, 'Экибастуз', 260, 'ekibastuz', '1395', '3', '1396', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (264, 54.4275017, 68.6491013, 'Северо-Казахстанская обл.', 1, 'severo-kazahstanskaja-oblast', '1398', '2', '1433', 'REGION', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (265, 54.898201, 70.4496994, 'Булаево', 264, 'bulaevo', '1403', '3', '1404', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (266, 54.9366989, 68.5395966, 'Мамлютка', 264, 'mamljutka', '1413', '3', '1414', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (267, 54.8737984, 69.1740036, 'Петропавловск', 264, 'petropavlovsk', '1419', '3', '1420', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (268, 53.8865013, 67.4173965, 'Саумалколь', 264, 'saumalkol', '1423', '3', '1424', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (269, 53.8471985, 69.7683029, 'Тайынша', 264, 'tajynsha', '1427', '3', '1428', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (270, 43.4954987, 68.4293976, 'Южно-Казахстанская обл.', 1, 'juzhno-kazahstanskaja-oblast', '1434', '2', '1505', 'REGION', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (271, 42.4173012, 68.8080978, 'Арысь', 270, 'arys', '1451', '3', '1452', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (272, 40.7756996, 68.3323975, 'Жетысай', 270, 'zhetysaj', '1457', '3', '1458', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (273, 43.5115013, 68.5105972, 'Кентау', 270, 'kentau', '1467', '3', '1468', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (274, 42.1813011, 69.8841019, 'Ленгер', 270, 'lenger', '1475', '3', '1476', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (275, 41.8339996, 69.0086975, 'Сарыагаш', 270, 'saryagash', '1483', '3', '1484', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (276, 43.2971992, 68.2587967, 'Туркестан', 270, 'turkestan', '1491', '3', '1492', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (277, 41.2653008, 67.9810028, 'Шардара', 270, 'shardara', '1501', '3', '1502', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (278, 42.3174019, 69.620697, 'Шымкент', 270, 'shymkent', '1435', '3', '1444', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (290, 43.4453011, 77.3320007, 'Саймасай', 132, 'sajmasaj', '827', '3', '828', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (291, 51.1226997, 71.3665009, 'Есильский р-н', 105, 'astana-esilskij', '307', '3', '310', 'DISTRICT', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (302, 43.259201, 77.7289963, 'Батан', 132, 'batan', '613', '3', '614', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (304, 43.3017006, 77.108902, 'Кызыл Кайрат', 132, 'kyzyl-kajrat', '783', '3', '784', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (305, 43.4135017, 76.8944016, 'Жапек батыр', 132, 'zhapek-batyr', '677', '3', '678', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (312, 43.4151001, 76.9665985, 'Ынтымак', 132, 'yntymak', '897', '3', '898', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (313, 43.3429985, 76.6697006, 'Междуреченск', 132, 'mezhdurechensk', '799', '3', '800', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (314, 43.4407005, 77.259903, 'Жанашар', 132, 'zhanashar', '673', '3', '674', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (316, 43.3023987, 77.0858994, 'Береке', 132, 'bereke', '617', '3', '618', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (318, 43.1299019, 76.8447037, 'Карагайлы (Чапаево)', 132, 'chapaevo', '713', '3', '714', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (321, 43.4201012, 76.9456024, 'Акши', 132, 'akshi', '563', '3', '564', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (324, 43.5584984, 76.9169998, 'Косозен', 132, 'kosozen', '759', '3', '760', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (325, 43.2257004, 76.3162003, 'Узынагаш', 132, 'uzynagash', '871', '3', '872', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (326, 43.1977005, 76.6480026, 'Музей Жамбыла', 132, 'muzej-zhambyla', '801', '3', '802', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (327, 43.3120995, 76.6691971, 'Улан', 132, 'ulan', '877', '3', '878', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (328, 50.8339005, 72.1821976, 'Аршалы', 112, 'arshaly', '327', '3', '328', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (329, 51.4888992, 69.7970963, 'Астраханка', 112, 'astrahanka', '329', '3', '330', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (330, 50.0393982, 54.0359001, 'Егиндыколь', 112, 'egindykol', '349', '3', '350', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (331, 51.9085007, 67.3162003, 'Жаксы', 112, 'zhaksy', '355', '3', '356', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (332, 52.9081993, 69.1549988, 'Зеренда', 112, 'zerenda', '367', '3', '368', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (333, 50.5882988, 70.0241013, 'Коргалжын', 112, 'korgalzhyn', '391', '3', '392', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (334, 52.5227013, 68.7555008, 'Балкашино', 112, 'balkashino', '335', '3', '336', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (335, 51.0643005, 70.975502, 'Акмол', 112, 'akmol', '325', '3', '326', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (336, 51.698101, 70.9974976, 'Шортанды', 112, 'shortandy', '459', '3', '460', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (337, 51.6769981, 71.0162964, 'Научный', 112, 'nauchnyj', '417', '3', '418', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (338, 51.7419014, 71.7161026, 'Жолымбет', 112, 'zholymbet', '363', '3', '364', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (341, 50.854599, 71.3584976, 'Кабанбай Батыра', 112, 'kabanbaj-batyra', '373', '3', '374', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (346, 43.2529984, 76.6523972, 'Долан', 132, 'dolan', '651', '3', '652', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (348, 43.7240982, 77.1259995, 'Арна', 132, 'arna', '581', '3', '582', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (350, 43.3130989, 76.0926971, 'Унгуртас', 132, 'ungurtas', '875', '3', '876', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (353, 43.4034996, 77.0631027, 'Кызыл ту-4', 132, 'kyzyl-tu-4', '781', '3', '782', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (355, 43.2487984, 76.4371033, 'Касымбек', 132, 'kasymbek', '727', '3', '728', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (357, 44.7247009, 76.2985001, 'Караой', 132, 'karaoj', '721', '3', '722', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (358, 43.2961998, 76.6564026, 'Мынбаев', 132, 'mynbaev', '803', '3', '804', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (360, 43.0878983, 76.9096985, 'Алма-Арасан', 132, 'alma-arasan', '569', '3', '570', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (361, 43.4385986, 76.9589005, 'Жанадауыр', 132, 'zhanadauyr', '671', '3', '672', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (362, 43.2331009, 76.6010971, 'Айтей', 132, 'ajtej', '549', '3', '550', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (363, 43.4996986, 77.2603989, 'Космос', 132, 'kosmos', '763', '3', '764', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (365, 43.2724991, 76.6738968, 'Жамбыл', 132, 'zhambyl', '665', '3', '666', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (366, 43.4001007, 77.2707977, 'Ават', 132, 'avat', '543', '3', '544', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (368, 51.2937012, 71.0203018, 'Максимовка', 112, 'maksimovka', '413', '3', '414', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (369, 51.2411995, 71.1656036, 'Талапкер', 112, 'talapker', '443', '3', '444', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (370, 51.2661018, 71.0774994, 'Кажымукан', 112, 'kazhymukan', '375', '3', '376', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (371, 49.7831001, 72.9576035, 'Актас', 235, 'aktas', '1147', '3', '1148', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (372, 43.3432007, 76.4024963, 'Улькен', 132, 'ulken', '873', '3', '874', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (373, 42.8688011, 73.1085968, 'Чиганак', 227, 'chiganak', '1087', '3', '1088', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (381, 43.5531006, 78.0131989, 'Ащысай', 132, 'ashhysaj', '589', '3', '590', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (391, 42.5984001, 69.2563019, 'Темирлановка', 270, 'temirlanovka', '1489', '3', '1490', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (392, 49.8256989, 72.6298981, 'Шахан', 235, 'shahan', '1203', '3', '1204', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (401, 49.5009003, 72.8071976, 'Топар', 235, 'topar', '1195', '3', '1196', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (403, 43.3506012, 77.0715027, 'Еламан', 132, 'elaman', '645', '3', '646', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (409, 43.2372017, 76.5139999, 'Енбекши', 132, 'enbekshi', '649', '3', '650', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (410, 43.4300995, 76.8542023, 'Комсомол', 132, 'komsomol', '761', '3', '762', 'CITY', '17');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (411, 43.4939995, 77.0154037, 'Жана-талап', 132, 'jana-talap', '663', '3', '664', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (418, 44.9640999, 77.9311981, 'Бекболат', 132, 'bekbolat', '611', '3', '612', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (419, 44.2503014, 77.7266006, 'Шубар', 132, 'shubar', '905', '3', '906', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (420, 43.3072014, 77.0166016, 'Талдыбулак', 132, 'taldybulak', '843', '3', '844', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (421, 43.512001, 77.6886978, 'Маловодное', 132, 'malovodnoe', '795', '3', '796', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (426, 43.2574005, 76.7697983, 'Коксай (Путь Ильича)', 132, 'koksaj', '747', '3', '748', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (435, 43.3795013, 77.2218018, 'Базаркельды', 132, 'bazarkel''dy', '595', '3', '596', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (436, 43.3437004, 76.9128036, 'Карасу-2', 132, 'karasu-2', '719', '3', '720', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (437, 43.3630981, 77.3071976, 'Кайназар', 132, 'kaynazar', '703', '3', '704', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (438, 51.4031982, 71.7073975, 'Софиевка', 112, 'sofievka', '433', '3', '434', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (439, 44.3647003, 77.9692993, 'Сарыозек', 132, 'saryozek', '837', '3', '838', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (440, 43.5136986, 76.8909988, 'Шелекемир', 132, 'shelekemir', '901', '3', '902', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (451, 43.050499, 74.7152023, 'Кордай', 227, 'kordaj', '1063', '3', '1064', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (452, 43.5349007, 75.2129974, 'Отар', 227, 'otar', '1055', '3', '1056', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (453, 43.6018982, 77.1023026, 'Даулет', 132, 'daulet', '641', '3', '642', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (454, 43.4869995, 77.4616013, 'Куш', 132, 'kush', '777', '3', '778', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (457, 43.6285019, 77.2556992, 'Остемир', 132, 'ostemir', '813', '3', '814', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (459, 44.1216011, 77.1099014, 'Сункар', 132, 'sunkar', '833', '3', '834', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (467, 42.4407997, 69.8495026, 'Аксукент', 270, 'аksukent', '1447', '3', '1448', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (471, 46.8919983, 81.5966034, 'Ельтай', 132, 'el''taj', '639', '3', '640', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (477, 48.5326996, 51.7532997, 'Индер', 213, 'inder', '933', '3', '934', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (479, 47.6682014, 51.5854988, 'Махамбет', 213, 'mahambet', '939', '3', '940', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (481, 53.3681984, 62.8647003, 'Качар', 247, 'kachar', '1247', '3', '1248', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (545, 43.2000999, 76.5615997, 'Шалкар', 132, 'alm-shalkar', '899', '3', '900', 'CITY', '17');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (547, 49.6264992, 83.5223007, 'Новая Бухтарма', 216, 'novaja buhtarma', '1001', '3', '1002', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (549, 51.4720001, 71.2068024, 'Бозайгыр', 112, 'bozajgyr', '333', '3', '334', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (551, 50.5648003, 72.5683975, 'Осакаровка', 235, 'osakarovka', '1179', '3', '1180', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (557, 43.294899, 76.8411026, 'Коккайнар', 132, 'kokkajnar', '739', '3', '740', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (559, 43.4747009, 77.1371002, 'Тонкерис', 132, 'tonkeris', '859', '3', '860', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (593, 43.3918991, 76.6667023, 'Жаугашты', 132, 'zhaugashty', '653', '3', '654', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (597, 43.4239006, 77.6231995, 'Каракемер', 132, 'karakemer', '717', '3', '718', 'CITY', '17');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (601, 52.4646988, 64.4714966, 'Кушмурун', 247, 'kushmurun', '1255', '3', '1256', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (603, 53.2005997, 63.6918983, 'Затобольск', 247, 'zatobol''sk', '1241', '3', '1242', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (627, 43.1525002, 75.9253998, 'Кумтоган', 132, 'kumtogan', '775', '3', '776', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (629, 45.0085983, 78.3961029, 'Балпык Би', 132, 'balpik-bi', '605', '3', '606', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (637, 51.2742996, 71.6427002, 'Коянды', 112, 'kojandy', '397', '3', '398', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (643, 43.9799995, 77.4626007, 'Шенгельды', 132, 'shengel''dy', '903', '3', '904', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (665, 43.232399, 76.6791992, 'Жалпаксай', 132, 'zhalpaksaj', '659', '3', '660', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (667, 51.7882996, 69.4076004, 'Жибек жолы', 112, 'zhibek zholy', '361', '3', '362', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (675, 43.1285019, 76.1007996, 'Каракастек', 132, 'karakastek', '715', '3', '716', 'CITY', '17');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (677, 43.419899, 76.9829025, 'Еркин', 132, 'erkin', '637', '3', '638', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (679, 44.5074997, 50.2627983, 'Форт-Шевченко', 257, 'fort-shevchenko', '1347', '3', '1348', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (681, 44.5442009, 50.2723999, 'Баутино', 257, 'Bautino', '1321', '3', '1322', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (683, 43.780899, 51.0798988, 'Аташ', 257, 'Atash', '1317', '3', '1318', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (685, 43.7820015, 51.0721016, 'Акшукур', 257, 'akshukur', '1313', '3', '1314', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (687, 43.8342018, 53.3526993, 'С.Шапагатова', 257, 's. shapagatova', '1343', '3', '1344', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (689, 43.3875999, 76.8738022, 'Кайнар', 132, 'kajnar', '701', '3', '702', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (693, 42.8610992, 73.1763, 'Мерке', 227, 'merke', '1067', '3', '1068', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (695, 42.9468002, 72.7434998, 'Курык', 257, 'kuryk', '1333', '3', '1334', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (697, 43.6296997, 77.8937988, 'Толкын', 132, 'tolkyn', '855', '3', '856', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (699, 49.1459999, 56.486599, 'Шубаркудук', 124, 'shubarkuduk', '523', '3', '524', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (725, 43.3949013, 76.9425964, 'Коянкус', 132, 'kojankus', '767', '3', '768', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (727, 52.7318001, 62.1472015, 'Федоровка', 247, 'fedorovka', '1285', '3', '1286', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (729, 52.3932991, 63.7821007, 'Аулиеколь', 247, 'auliekol''', '1221', '3', '1222', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (731, 50.9743996, 71.3520966, 'Косшы', 112, 'kosshy', '393', '3', '394', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (733, 43.202301, 76.4284973, 'Ульгили', 132, 'ul''gili', '879', '3', '880', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (735, 49.2131004, 84.5106964, 'Улкен Нарын', 216, 'ulken naryn', '1027', '3', '1028', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (737, 50.4109001, 80.2640991, 'Катон-Карагай', 216, 'katon-karagaj', '987', '3', '988', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (739, 52.9744987, 68.3622971, 'Имантау', 264, 'imantau', '1409', '3', '1410', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (757, 43.1850014, 76.6512985, 'Наурыз', 132, 'nauryz', '805', '3', '806', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (759, 53.0625992, 62.5330009, 'Тобол', 247, 'tobol', '1277', '3', '1278', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (761, 49.9115982, 72.8487015, 'Дубовка', 235, 'dubovka', '1157', '3', '1158', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (767, 43.4026985, 77.240303, 'Кырбалтабай', 132, 'kyrbaltabaj', '791', '3', '792', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (769, 49.1805992, 58.4194984, 'Булакты', 132, 'bulakty', '631', '3', '632', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (771, 51.3381004, 71.0931015, 'Фарфоровое', 112, 'farforovoe', '455', '3', '456', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (775, 50.8893013, 71.8728027, 'Кызылсуат', 112, 'kyzylsuat', '405', '3', '406', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (781, 50.0688019, 82.3925018, 'Белоусовка', 216, 'belousovka', '965', '3', '966', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (783, 43.1892014, 76.8169022, 'Наурызбайский р-н', 2, 'nauryzbajskiy', '257', '3', '284', 'DISTRICT', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (787, 43.3330994, 76.7779999, 'Исаево', 132, 'isaevo', '693', '3', '694', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (789, 49.9123993, 72.5314026, 'Кобетей', 235, 'kobetej', '1167', '3', '1168', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (791, 47.7677994, 81.5199966, 'Урджар', 216, 'urdzhar', '1029', '3', '1030', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (793, 50.1352997, 81.1801987, 'Шульбинск', 216, 'shul''binsk', '1039', '3', '1040', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (795, 45.3167, 55.2000008, 'Бейнеу', 257, 'bejneu', '1327', '3', '1328', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (815, 43.4669991, 78.3459015, 'Асы-Сага', 132, 'asy-saga', '583', '3', '584', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (817, 53.2837982, 63.8306999, 'Заречное', 247, 'kost-zarechnoe', '1239', '3', '1240', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (819, 53.1615982, 70.1980972, 'Жукей', 112, 'zhukej', '365', '3', '366', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (881, 51.2116013, 71.1137009, 'Воздвиженка', 112, 'vozdvizhenka', '341', '3', '342', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (885, 0, 0, 'Байконур', 253, 'bajkonur', '1293', '3', '1296', 'CITY', '0');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (983, 54.7425995, 69.8391037, 'Виноградовка', 264, 'vinogradovka', '1405', '3', '1406', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (985, 43.3390999, 77.4377975, 'Койшыбек', 132, 'kojshybek', '735', '3', '736', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (989, 44.4729996, 51.4990997, 'Шетпе', 257, 'shetpe', '1349', '3', '1350', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1047, 43.3109016, 76.9107971, 'Тескенсу', 132, 'teskensu', '853', '3', '854', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1053, 44.9269981, 78.4003983, 'Бактыбая Жолбарысулы', 132, 'baktybaya-zholbarysuly', '603', '3', '604', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1079, 35.2146988, 33.6287994, 'Тала', NULL, 'tala', '2028', '2', '2029', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1085, 45.2150993, 77.3393021, 'Байбулак', 132, 'bajbulak', '593', '3', '594', 'CITY', '5');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1129, 52.8666992, 63.4877014, 'Сарыколь', 247, 'sarykol', '1271', '3', '1272', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1135, 42.0834007, 69.8559036, 'Карабулак', 270, 'uko-karabulak', '1461', '3', '1462', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1153, 49.5489006, 83.6485977, 'Глубокое', 216, 'glubokoe', '975', '3', '976', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1177, 43.4785004, 77.0022964, 'Коктерек', 132, 'kokterek', '751', '3', '752', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1179, 51.0032005, 71.5465012, 'Куйгенжар', 112, 'kujgenzhar', '403', '3', '404', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1181, 52.2549019, 70.6692963, 'Катарколь', 112, 'katarkol', '381', '3', '382', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1205, 53.0456009, 63.9799004, 'Денисовка', 247, 'denisovka', '1231', '3', '1232', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1217, 43.3596992, 76.6159973, 'Кольащы', 132, 'kolashy', '755', '3', '756', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1219, 48.8246994, 81.2285004, 'им. Касыма Кайсенова', 216, 'im.-kasyma-kajsenova', '989', '3', '990', 'CITY', '5');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1229, 42.8540993, 71.1682968, 'Сарыкемер', 227, 'sarykemer', '1071', '3', '1072', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1277, 50.9514008, 71.7963028, 'Узунколь', 112, 'uzunkol', '453', '3', '454', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1279, 52.7751007, 63.615799, 'Узунколь', 247, 'uzunkoll', '1283', '3', '1284', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1281, 43.3622017, 76.6579971, 'Екпенды', 132, 'ekpendy', '643', '3', '644', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1283, 49.7619019, 72.8665009, 'Ботакара', 235, 'botakara', '1155', '3', '1156', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1289, 53.0928001, 63.3833008, 'Камысты', 247, 'kamysty', '1243', '3', '1244', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1291, 49.5919991, 72.9049988, 'Улытау', 235, 'ulytau', '1199', '3', '1200', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1311, 49.9323006, 50.6645012, 'Каратобе', 232, 'karatobe', '1107', '3', '1108', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1313, 52.5542984, 62.5517006, 'Владимировка', 247, 'vladimirovka', '1227', '3', '1228', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1315, 50.9389992, 71.2324982, 'Рахымжана Кошкарбаева', 112, 'raxymzhana-koshkarbaeva', '427', '3', '428', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1317, 54.1446991, 69.2093964, 'Сергеевка', 264, 'sergeevka', '1425', '3', '1426', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1319, 43.4762001, 77.4643021, 'Енбек', 132, 'enbek', '647', '3', '648', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1323, 52.4864006, 62.5619011, 'Боровской', 247, 'borovskoj', '1225', '3', '1226', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1329, 53.7542, 63.7438011, 'Надеждинка', 247, 'nadezhdinka', '1265', '3', '1266', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1333, 47.2370987, 51.6104012, 'Доссор', 213, 'dossor', '927', '3', '928', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1335, 49.5074997, 72.927597, 'Агадырь', 235, 'agadyr', '1145', '3', '1146', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1341, 43.6888008, 77.273201, 'Аккайнар', 132, 'akkainar', '555', '3', '556', 'MICRODISTRICT', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1343, 43.4155006, 77.186203, 'Алга', 132, 'algas', '565', '3', '566', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1345, 42.9840012, 72.6386032, 'Кулан', 227, 'kulan', '1065', '3', '1066', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1347, 53.0625992, 62.5330009, 'Ульяновское', 247, 'ulyanovskoe', '1281', '3', '1282', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1355, 42.5489998, 70.1568985, 'Тюлькубас', 270, 'tyulkubasskiy-raion', '1493', '3', '1494', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1363, 52.8064995, 63.467701, 'Тарановское', 247, 'taranovskoe', '1275', '3', '1276', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1365, 52.6824989, 63.3620987, 'Мичуринское', 247, 'michurinskoe', '1261', '3', '1262', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1367, 43.2887001, 77.2291031, 'Алмалык', 132, 'almalyk', '573', '3', '574', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1373, 51.8849983, 69.8575974, 'Каражар', 112, 'karazhar', '377', '3', '378', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1377, 42.9788017, 71.5239029, 'Байзак', 227, 'bajzak', '1053', '3', '1054', 'DISTRICT', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1381, 43.4668999, 77.4077988, 'Нияз', 132, 'niyaz', '807', '3', '808', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1385, 43.3787003, 77.2385025, 'Актас', 132, 'aktas1', '557', '3', '558', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1389, 48.9737015, 71.6043015, 'Кокпекты', 235, 'kokpekty', '1171', '3', '1172', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1393, 43.3501015, 77.0820999, 'Кызылту', 132, 'kyzyltu', '787', '3', '788', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1395, 49.9569016, 57.0014992, 'Мартук', 124, 'martuk', '503', '3', '504', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1397, 51.0904999, 71.1747971, 'Караоткель', 112, 'karaotkel', '379', '3', '380', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1399, 43.3605995, 77.004303, 'Айганым', 132, 'ajganym', '545', '3', '546', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1401, 43.9584007, 76.1828995, 'Бастобе', 132, 'bastobe', '609', '3', '610', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1403, 51.9453011, 76.1308975, 'Коктобе', 260, 'koktobe', '1367', '3', '1368', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1405, 43.3745003, 77.4738998, 'Коктобе', 132, 'kokto', '749', '3', '750', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1407, 52.0808983, 76.7461014, 'Черноярка', 260, 'chernoyarka', '1389', '3', '1390', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1409, 43.3889008, 77.1615982, 'Кендала', 132, 'kendala', '731', '3', '732', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1411, 43.5096016, 52.1062012, 'Баскудук', 257, 'baskuduk', '1319', '3', '1320', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1413, 50.4371986, 80.3139038, 'Аксаринский кордон', 216, 'aksarinskij-kordon', '963', '3', '964', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1415, 43.4898987, 52.0836983, 'Мунайши', 257, 'munajshi', '1339', '3', '1340', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1417, 51.2535019, 51.3665009, 'Мичурино', 232, 'michurino', '1117', '3', '1118', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1419, 45.2081985, 78.1149979, 'Кокдала', 132, 'kokdala', '737', '3', '738', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1421, 50.4193993, 57.0852013, 'Курайлы', 124, 'kurajly', '495', '3', '496', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1423, 42.8418007, 71.4503021, 'Жалпактобе', 227, 'zhalpaktobe', '1059', '3', '1060', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1425, 53.2398987, 69.5334015, 'Красный яр', 112, 'krasnyj-yar', '401', '3', '402', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1427, 43.6688004, 51.2285004, 'Батыр', 257, 'batyr', '1323', '3', '1324', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1429, 0, 0, 'Кызылтобе', 257, 'kyzyltobe', '1337', '3', '1338', 'CITY', '0');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1431, 43.546299, 71.1119003, 'Шайкорык', 227, 'shajkoryk', '1077', '3', '1078', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1433, 50.5862007, 71.0194016, 'Тайтобе', 112, 'tajtobe', '439', '3', '440', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1437, 50.4328995, 57.1591988, 'Пригородный', 124, 'prigorodnyj', '509', '3', '510', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1439, 50.3675995, 57.7056999, 'Садовое', 124, 'sadovoe', '511', '3', '512', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1443, 50.5452995, 50.9500999, 'Пугачево', 232, 'pugachevo', '1121', '3', '1122', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1445, 47.3641014, 52.4040985, 'Акжар', 213, 'akzharа', '913', '3', '914', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1447, 47.4566994, 51.8120003, 'Еркинкала', 213, 'erkinkala', '929', '3', '930', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1449, 52.2970009, 77.0098038, 'Майкайын', 260, 'majkajyn', '1373', '3', '1374', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1453, 52.6358986, 70.3867035, 'Успеноюрьевка', 112, 'uspenoyurevka', '451', '3', '452', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1455, 50.6711006, 51.2578011, 'Деркул', 232, 'derkul', '1101', '3', '1102', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1457, 51.1353989, 51.0556984, 'Федоровка', 232, 'fedorovka1', '1129', '3', '1130', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1459, 44.6534004, 65.4449005, 'Тасбогет', 253, 'tasboget', '1307', '3', '1308', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1461, 51.7979012, 64.2655029, 'Антоновка', 247, 'antonovka', '1217', '3', '1218', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1463, 49.7126999, 82.0379028, 'Аккайнар', 216, 'akkajnar', '957', '3', '958', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1465, 43.4706993, 76.7751999, 'Чапаево', 132, 'chapaevo1', '891', '3', '892', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1473, 50.7508011, 50.8236008, 'Белес', 232, 'beles', '1097', '3', '1098', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1477, 49.9306984, 82.2774963, 'Герасимовка', 216, 'gerasimovka', '973', '3', '974', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1479, 49.5494003, 82.6137009, 'Восточный', 216, 'vostochnyj', '971', '3', '972', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1481, 50.3278008, 50.763401, 'Зачаганск', 232, 'zachagansk', '1103', '3', '1104', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1483, 47.3641014, 52.2832985, 'Ракуша', 213, 'rakusha', '943', '3', '944', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1485, 52.3507996, 77.1635971, 'Шидерты', 260, 'shiderty', '1393', '3', '1394', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1487, 49.0707016, 81.5580978, 'Бобровка', 216, 'bobrovka', '967', '3', '968', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1489, 43.9124985, 77.3713989, 'Казахстан', 132, 'kazaxstan', '705', '3', '706', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1493, 43.5004997, 77.0094986, 'Новый', 132, 'novyj', '809', '3', '810', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1495, 53.3042984, 68.8563995, 'Кызылжулдуз', 112, 'kyzylzhulduz', '407', '3', '408', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1497, 44.0887985, 52.986599, 'Мангышлак', 257, 'mangyshlak', '1335', '3', '1336', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1499, 51.4546013, 70.3300018, 'Тельмана', 112, 'telmana', '447', '3', '448', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1501, 43.4985008, 75.1999969, 'Алга', 227, 'alga1', '1049', '3', '1050', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1503, 52.4967995, 65.0823975, 'Дружба', 247, 'druzhba', '1233', '3', '1234', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1505, 45.4160004, 64.1895981, 'Махамбетов', 253, 'maxambetov', '1305', '3', '1306', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1507, 44.1699982, 54.1362, 'Баянды', 257, 'bayandy', '1325', '3', '1326', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1509, 43.9031982, 68.6837006, 'Таскен', 270, 'tasken', '1485', '3', '1486', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1511, 43.7354012, 76.5076981, 'Толе би', 132, 'tole-bi', '857', '3', '858', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1513, 44.1478004, 68.7149963, 'Алтынтобе', 270, 'altyntobe', '1449', '3', '1450', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1515, 43.8396988, 71.3557968, 'Шиен', 227, 'shien', '1079', '3', '1080', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1517, 53.5223007, 64.4896011, 'Алтын-Дала', 247, 'altyn-dala', '1223', '3', '1224', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1519, 49.3442993, 81.733902, 'Коктерек', 216, 'kokterek1', '991', '3', '992', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1521, 43.3291016, 77.2130966, 'Шымбулак', 132, 'shymbulak', '907', '3', '908', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1523, 49.9935989, 56.9273987, 'Кызылжар', 124, 'kyzylzhar1', '499', '3', '500', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1525, 43.9005013, 54.6856003, 'Жетыбай', 257, 'zhetybaj', '1331', '3', '1332', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1537, 51.8366013, 76.1747971, 'Успенка', 260, 'uspenka', '1391', '3', '1392', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1539, 52.2438011, 70.9891968, 'Дамса', 112, 'damsa', '347', '3', '348', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1541, 48.4580002, 75.8464966, 'Коктас', 235, 'koktas', '1169', '3', '1170', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1543, 43.986599, 77.615303, 'Али', 132, 'ali', '567', '3', '568', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1545, 52.3238983, 76.7900009, 'Ленинский', 260, 'leninskij', '1369', '3', '1370', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1547, 48.6477013, 75.6339035, 'Шахтерское', 235, 'shaxterskoe', '1207', '3', '1208', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1549, 49.6344986, 57.1032982, 'Жилянка', 124, 'zhilyanka', '485', '3', '486', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1551, 43.7686996, 77.504097, 'Болек', 132, 'bolek', '627', '3', '628', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1553, 47.394001, 52.4151001, 'Дамба', 213, 'damba', '925', '3', '926', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1555, 45.6174011, 77.3612976, 'Уштерек', 132, 'ushterek', '889', '3', '890', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1559, 43.0136986, 68.7919006, 'Ынтымак', 270, 'yntymak2', '1503', '3', '1504', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1561, 42.8279991, 69.3302994, 'Кутарыс', 270, 'kutarys', '1465', '3', '1466', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1563, 53.0443993, 70.0113983, 'Волгодоновка', 112, 'volgodonovka', '343', '3', '344', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1567, 49.0852013, 81.4701996, 'Ахмерово', 216, 'axmerovo', '959', '3', '960', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1569, 53.050499, 64.6799011, 'Челгаши', 247, 'chelgashi', '1287', '3', '1288', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1571, 44.1055984, 71.4726028, 'Орнек', 227, 'ornek', '1069', '3', '1070', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1573, 47.5876007, 52.8325996, 'Алмалы', 213, 'almaly1', '917', '3', '918', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1575, 49.2436981, 81.7778015, 'Топиха', 216, 'topixa', '1025', '3', '1026', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1577, 50.3067017, 50.6315002, 'Трекино', 232, 'trekino', '1125', '3', '1126', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1579, 48.3893013, 75.1411972, 'Мынбаев', 235, 'mynbaev1', '1173', '3', '1174', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1581, 49.0113983, 71.1388016, 'Сортировка', 235, 'sortirovka', '1191', '3', '1192', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1583, 48.9696007, 81.6679993, 'Ушановский', 216, 'ushanovskij', '1031', '3', '1032', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1585, 51.0186996, 77.4352036, 'Баянаул', 260, 'bayanaul', '1357', '3', '1358', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1589, 50.4191017, 50.9062004, 'Подстепное', 232, 'podstepnoe', '1119', '3', '1120', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1591, 49.5693016, 57.2123985, 'Сарыжар', 124, 'saryzhar', '515', '3', '516', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1593, 51.2481995, 71.9120026, 'Ильинка', 112, 'ilinka', '369', '3', '370', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1595, 52.3111992, 71.2309036, 'Красный Кордон', 112, 'krasnyj-kordon', '399', '3', '400', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1597, 45.9160004, 81.4850006, 'Коктума', 132, 'koktuma', '753', '3', '754', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1599, 43.5931015, 77.0660019, 'Жомарт', 132, 'zhomart', '687', '3', '688', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1601, 44.3702011, 78.4378967, 'Теренкара', 132, 'terenkara', '851', '3', '852', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1603, 49.3442993, 81.4263, 'Куленовка', 216, 'kulenovka', '993', '3', '994', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1605, 49.3442993, 81.8436966, 'Первомайский', 216, 'pervomajskij', '1007', '3', '1008', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1609, 42.2022018, 69.0366974, 'Мырзакент', 270, 'myrzakent', '1479', '3', '1480', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1611, 52.1484985, 76.9878006, 'Луганск', 260, 'lugansk', '1371', '3', '1372', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1613, 44.2294006, 79.8795013, 'Головацкий', 132, 'golovaczkij', '635', '3', '636', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1615, 51.4553986, 70.7286987, 'Кенесары', 112, 'kenesary', '383', '3', '384', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1617, 52.0965996, 64.5072021, 'Московское ', 247, 'moskovskoe-', '1263', '3', '1264', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1621, 52.3373985, 77.0756989, 'Павлодарское', 260, 'pavlodarskoe', '1377', '3', '1378', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1623, 44.6769981, 78.2296982, 'Жетысу', 132, 'zhetysu', '685', '3', '686', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1625, 43.2910004, 72.6176987, 'Кокибель', 270, 'kokibel', '1469', '3', '1470', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1627, 51.2067986, 71.5384979, 'Малотимофеевка', 112, 'malotimofeevka', '415', '3', '416', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1629, 52.4718018, 77.0317001, 'Ямышево', 260, 'yamyshevo', '1375', '3', '1376', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1631, 43.5914001, 69.3082962, 'Асыката', 270, 'asykata', '1453', '3', '1454', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1635, 52.1357994, 70.0003967, 'Акбулак', 112, 'akbulak1', '321', '3', '322', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1637, 54.5363007, 68.9347, 'Зарослое', 264, 'zarosloe', '1407', '3', '1408', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1639, 0, 0, 'Альжан', 264, 'alzhan', '1399', '3', '1400', 'CITY', '0');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1641, 52.4645996, 77.6697006, 'Жетекши', 260, 'zhetekshi', '1359', '3', '1360', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1645, 54.5425987, 68.8687973, 'Уваковское', 264, 'uvakovskoe', '1431', '3', '1432', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1647, 54.8086014, 69.3684998, 'Тельманово', 264, 'telmanovo', '1429', '3', '1430', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1649, 50.6250992, 80.6889038, 'Шакаман', 216, 'shakaman', '1033', '3', '1034', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1651, 52.3642998, 77.2954025, 'Прииртышское', 260, 'priirtyshskoe', '1381', '3', '1382', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1653, 43.1212006, 77.3451996, 'Арай', 132, 'araj', '579', '3', '580', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1655, 52.4724998, 70.6815033, 'Приречное', 112, 'prirechnoe', '425', '3', '426', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1657, 49.8807983, 57.6049004, 'Сазда', 124, 'sazda', '513', '3', '514', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1659, 51.7705994, 64.3753967, 'Михайловка', 247, 'mixajlovka', '1259', '3', '1260', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1661, 52.6422997, 71.3080978, 'Отрадное', 112, 'otradnoe', '423', '3', '424', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1663, 52.229599, 77.3394012, 'Солнечный', 260, 'solnechnyj', '1383', '3', '1384', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1665, 47.5205994, 52.4151001, 'Ганюшкино', 213, 'ganyushkino', '923', '3', '924', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1667, 49.774601, 83.0221024, 'Ивановка', 216, 'ivanovka', '985', '3', '986', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1669, 43.9258003, 69.1545029, 'Улгили', 270, 'ulgili', '1495', '3', '1496', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1671, 43.2069016, 68.7590027, 'Састобе', 270, 'sastobe', '1481', '3', '1482', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1673, 42.8711014, 69.3421021, 'им. Турара Рыскулова', 270, 'im.-turara-ryskulova', '1459', '3', '1460', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1675, 43.463501, 68.9567032, 'Шакпак баба', 270, 'shakpak-baba', '1499', '3', '1500', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1677, 44.2857018, 77.8585968, 'им.Балгабека Кыдырбекулы', 132, 'im.balgabeka-kydyrbekuly', '691', '3', '692', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1679, 47.6767006, 52.6787987, 'Береке (Память Ильича)', 213, 'bereke-(pamyat-ilicha)', '919', '3', '920', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1681, 49.2436981, 83.7526016, 'Тарханка', 216, 'tarxanka', '1023', '3', '1024', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1683, 50.3208008, 50.7743988, 'Дарьинск', 232, 'darinsk', '1099', '3', '1100', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1685, 49.0707016, 81.6899033, 'Ново-Явленка', 216, 'novo-yavlenka', '1003', '3', '1004', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1687, 52.3103981, 76.1968002, 'Парамоновка', 260, 'paramonovka', '1379', '3', '1380', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1691, 48.1926994, 75.4916, 'Ахмет', 235, 'axmet', '1149', '3', '1150', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1693, 43.4385986, 71.2988968, 'Тамды', 227, 'tamdy', '1073', '3', '1074', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1695, 43.655201, 69.6379013, 'Жаскешу', 270, 'zhaskeshu', '1455', '3', '1456', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1699, 49.6887016, 81.8740005, 'Меновное', 216, 'menovnoe', '999', '3', '1000', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1703, 43.7350006, 69.2643967, 'Акжол', 270, 'akzhol', '1445', '3', '1446', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1705, 42.3123016, 69.2417984, 'Кулан', 270, 'kulan1', '1473', '3', '1474', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1707, 45.3580017, 63.6063995, 'Казылжарма', 253, 'kazylzharma', '1301', '3', '1302', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1709, 49.4333, 57.4211006, 'Жанаконыс', 124, 'zhanakonys', '483', '3', '484', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1713, 50.0881004, 50.7523994, 'Малый Чаган', 232, 'malyj-chagan', '1115', '3', '1116', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1715, 44.0909004, 54.0703011, 'Умирзак', 257, 'umirzak', '1345', '3', '1346', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1717, 52.1890984, 76.9878006, 'Кенжеколь', 260, 'kenzhekol', '1365', '3', '1366', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1719, 51.7024002, 64.2216034, 'Озерное', 247, 'ozernoe', '1267', '3', '1268', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1721, 48.0940018, 74.8125, 'Новодолинск', 235, 'novodolinsk', '1177', '3', '1178', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1723, 44.4135017, 72.6042023, 'Алатау', 227, 'alatau', '1047', '3', '1048', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1725, 44.7159004, 77.1855011, 'Акдала', 132, 'akdala', '553', '3', '554', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1727, 52.0808983, 77.1195984, 'Заря', 260, 'zarya', '1361', '3', '1362', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1729, 51.8931007, 64.1557007, 'Убаган', 247, 'ubagan', '1279', '3', '1280', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1735, 43.4127998, 77.097702, 'Амангельды', 132, 'amangeldy', '577', '3', '578', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1745, 45.2563019, 77.9731979, 'Уштобе', 235, 'karagandinskaja-oblast-ushtobe', '1201', '3', '1202', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1747, 53.287899, 63.6813011, 'Жамбыл', 247, 'kostanajskaja-oblast-zhambyl', '1235', '3', '1236', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1749, 47.8373985, 80.3780975, 'Жиланды', 216, 'zhilandy', '979', '3', '980', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1753, 42.3064995, 69.0851974, 'Тассай', 270, 'tassaj', '1487', '3', '1488', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1755, 48.9117012, 82.0195007, 'Пригородное', 216, 'prigorodnoe', '1009', '3', '1010', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1757, 47.3492012, 52.4151001, 'Талгайран', 213, 'talgajran', '945', '3', '946', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1759, 48.4459991, 75.7792969, 'Березняки', 235, 'bereznyaki', '1153', '3', '1154', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1763, 48.2849998, 75.1201019, 'Шашубай', 235, 'shashubaj', '1209', '3', '1210', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1765, 54.073101, 68.1378021, 'Чайкино', 112, 'chajkino', '457', '3', '458', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1769, 50.2995987, 50.763401, 'Круглоозерное', 232, 'krugloozernoe', '1109', '3', '1110', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1773, 45.0115013, 68.7590027, 'Карасу', 270, 'karasu', '1463', '3', '1464', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1777, 44.0500984, 70.9782028, 'Чайкурук', 227, 'chajkuruk', '1085', '3', '1086', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1779, 44.2349014, 71.2455978, 'Шолаккаргалы', 227, 'sholakkargaly', '1081', '3', '1082', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1781, 44.7302017, 69.4181976, 'Мартобе', 270, 'martobe', '1477', '3', '1478', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1783, 43.5211983, 77.1920013, 'Аймен', 132, 'ajmen', '547', '3', '548', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1785, 43.7086983, 77.5053024, 'Актоган', 132, 'aktogan', '559', '3', '560', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1787, 47.4238014, 52.360199, 'Аксай', 213, 'aksaj1', '915', '3', '916', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1789, 47.4611015, 52.448101, 'Жумыскер', 213, 'zhumysker', '931', '3', '932', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1791, 49.3730011, 81.3823013, 'Прапорщиково', 216, 'praporshikovo', '1011', '3', '1012', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1793, 49.3903008, 57.0586014, 'Маржанбулак', 124, 'marzhanbulak', '501', '3', '502', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1795, 49.2723999, 81.2944031, 'Горная Ульбинка', 216, 'gornaya-ulbinka', '977', '3', '978', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1797, 51.8917999, 71.2748032, 'Сабынды', 112, 'sabyndy', '429', '3', '430', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1799, 47.3790016, 52.3051987, 'Бирлик', 213, 'birlik2', '921', '3', '922', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1803, 46.1155014, 73.6175995, 'Сарышаган', 235, 'saryshagan', '1187', '3', '1188', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1805, 52.3777008, 76.9218979, 'Теренколь', 260, 'terenkol', '1385', '3', '1386', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1809, 47.9737015, 73.9023972, 'Кенгир', 235, 'kengir', '1165', '3', '1166', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1811, 44.1699982, 52.9936981, 'Атамекен', 257, 'atameken', '1315', '3', '1316', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1817, 44.8916016, 65.5811005, 'Досан', 253, 'dosan', '1297', '3', '1298', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1819, 43.2569008, 76.2522964, 'Кызылсок', 132, 'kyzylsok', '785', '3', '786', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1823, 53.1297989, 63.5674019, 'Красный партизан', 247, 'krasnyj-partizan', '1253', '3', '1254', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1825, 51.1245995, 71.5964966, 'Интернациональный', 112, 'internaczionalnyj', '371', '3', '372', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1831, 50.0385017, 82.5873032, 'Винное', 216, 'vinnoe', '969', '3', '970', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1833, 47.5205994, 52.6678009, 'Коктагай', 213, 'koktagaj', '935', '3', '936', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1837, 53.0470009, 70.5507965, 'Шубар', 112, 'shubar1', '461', '3', '462', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1841, 50.6707993, 51.0158005, 'Новенький', 232, 'novenkij', '1113', '3', '1114', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1843, 44.9480019, 78.471199, 'Карабулак', 132, 'karabulak2', '711', '3', '712', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1845, 49.4160004, 81.8218002, 'Радужное', 216, 'raduzhnoe', '1013', '3', '1014', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1849, 47.6915016, 52.5578995, 'Томарлы', 213, 'tomarly', '947', '3', '948', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1851, 49.332901, 57.3003006, 'Курашасай', 124, 'kurashasaj', '497', '3', '498', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1853, 49.4189987, 57.3223, 'Аккемер', 124, 'akkemer', '479', '3', '480', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1855, 52.4718018, 76.6582031, 'Чернорецкое', 260, 'chernoreczkoe', '1387', '3', '1388', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1857, 50.9571991, 51.4159012, 'Барбастау', 232, 'barbastau', '1095', '3', '1096', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1859, 49.8956985, 57.2563019, 'Нурбулак', 124, 'nurbulak', '507', '3', '508', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1863, 50.3112984, 83.7305984, 'Лесхоз', 216, 'lesxoz', '997', '3', '998', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1867, 47.4760017, 52.5359993, 'Миялы', 213, 'miyaly', '941', '3', '942', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1869, 49.3114014, 57.289299, 'Ясное', 124, 'yasnoe', '527', '3', '528', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1871, 49.3759995, 57.2783012, 'Заречный', 124, 'zarechnyj1', '487', '3', '488', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1875, 50.9718018, 72.0733032, 'Константиновка', 112, 'konstantinovka', '389', '3', '390', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1877, 44.1767998, 71.2857971, 'Акыртобе', 227, 'akyrtobe', '1045', '3', '1046', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1879, 43.8623009, 68.7149963, 'Черноводск', 270, 'chernovodsk', '1497', '3', '1498', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1883, 54.5298996, 68.6270981, 'Киялы', 264, 'kiyaly', '1411', '3', '1412', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1885, 50.1162987, 50.6206017, 'Асан', 232, 'asan', '1093', '3', '1094', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1887, 51.6579018, 64.2292023, 'Караменды', 247, 'karamendy', '1245', '3', '1246', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1897, 48.4168015, 75.955101, 'Самарка', 235, 'samarka', '1183', '3', '1184', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1899, 45.6805, 79.2170029, 'Коктал', 132, 'koktal', '743', '3', '744', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1901, 46.1544991, 78.1743011, 'Капал', 132, 'kapal', '707', '3', '708', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1903, 54.4978981, 69.066597, 'Новопавловка', 264, 'novopavlovka', '1417', '3', '1418', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (1905, 46.4891014, 74.5289993, 'Торангалык', 235, 'torangalyk', '1197', '3', '1198', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33513, 42.8768997, 74.5968018, 'Бишкек', 33511, 'bishkek', '1532', '2', '1533', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33515, 42.6525002, 77.0748978, 'Чолпон-Ата', 33511, 'cholpon-ata', '1538', '2', '1539', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33517, 55.1997986, 91.6435013, 'Россия', NULL, 'rossija', '1551', '1', '1644', 'COUNTRY', '2');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33519, 55.7439003, 37.5853004, 'Москва', 33517, 'moskva', '1588', '2', '1589', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33521, 59.9478989, 30.2203007, 'Санкт-Петербург', 33517, 'sankt-peterburg', '1614', '2', '1615', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33523, 49.9566994, 15.1665001, 'Чехия', NULL, 'chehija', '2303', '1', '2308', 'COUNTRY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33525, 43.3484993, 76.9243011, 'мкр Кокжиек', 83, 'almaty-zhetysuskij-kokzhiek', '216', '4', '217', 'MICRODISTRICT', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33527, 43.196701, 76.8775024, 'мкр Орбита-4', 73, 'almaty-bostandykskij-orbita-4', '198', '4', '199', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33529, 43.3455009, 76.9141006, 'мкр Карасу', 3, 'almaty-alatauskij-mkr-karasu', '34', '4', '35', 'MICRODISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33531, 43.3073997, 76.8992996, 'мкр Дорожник', 83, 'almaty-zhetysuskij-dorozhnik', '212', '4', '213', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33533, 42.4367981, 25.0799999, 'Болгария', NULL, 'bolgarija', '1681', '1', '1724', 'COUNTRY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33535, 54.8093987, -2.99275994, 'Великобритания', NULL, 'velikobritanija', '1725', '1', '1742', 'COUNTRY', '5');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33537, 47.1921997, 19.3644009, 'Венгрия', NULL, 'vengrija', '1743', '1', '1746', 'COUNTRY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33539, 16.3015995, 101.362, 'Таиланд', NULL, 'tailand', '2189', '1', '2196', 'COUNTRY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33541, 38.5768013, 34.6464005, 'Турция', NULL, 'turcija', '2197', '1', '2220', 'COUNTRY', '5');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33543, 40.3745003, -4.28915024, 'Испания', NULL, 'ispanija', '1843', '1', '1960', 'COUNTRY', '5');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33545, 38.8681984, 23.0447998, 'Греция', NULL, 'grecija', '1769', '1', '1790', 'COUNTRY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33547, 47.0351982, 2.69814992, 'Франция', NULL, 'francija', '2223', '1', '2268', 'COUNTRY', '5');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33549, 24.2402992, 44.7098999, 'Саудовская Аравия', NULL, 'saudovskaja-aravija', '2151', '1', '2152', 'COUNTRY', '5');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33551, 24.3003998, 54.1142006, 'ОАЭ', NULL, 'oae', '2117', '1', '2132', 'COUNTRY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33553, 43.6565018, 11.8804998, 'Италия', NULL, 'italija', '1961', '1', '2026', 'COUNTRY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33555, 67.1047974, -94.0958023, 'Канада', NULL, 'kanada', '2027', '1', '2030', 'COUNTRY', '3');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33557, 39.4978981, -99.0792007, 'США', NULL, 'usa', '2177', '1', '2188', 'COUNTRY', '0');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33559, 48.4636002, 31.6860008, 'Украина', NULL, 'ukraina', '1655', '1', '1662', 'COUNTRY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33561, 57.3250008, 24.3743992, 'Латвия', NULL, 'latvija', '1541', '1', '1548', 'COUNTRY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33563, 43.3530006, 77.1457977, 'мкр Алатау (ИЯФ)', 89, 'almaty-medeuskij-alatau', '222', '4', '223', 'MICRODISTRICT', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33565, 43.2126007, 76.8458023, 'мкр Мамыр-4', 29, 'almaty-aujezovskij-mkr-mamyr-4', '144', '4', '145', 'MICRODISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33567, 43.2126007, 76.8458023, 'мкр Мамыр-2', 29, 'almaty-aujezovskij-mkr-mamyr-2', '140', '4', '141', 'MICRODISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33569, 42.7154999, 27.7581997, 'Святой Влас', 33533, 'svjatoj-vlas', '1712', '2', '1713', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33571, 43.581501, 39.7229004, 'Сочи', 33517, 'sochi', '1622', '2', '1623', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33573, 43.2955017, 76.6792984, 'Кольди', 132, 'koldi', '765', '3', '766', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33575, 43.2061005, 76.9873962, 'мкр Юбилейный', 89, 'almaty-medeuskij-jubilejnyj', '230', '4', '231', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33577, 36.8041, 34.6240005, 'Мерсин', 33541, 'mersin', '2216', '2', '2217', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33579, 51.2240982, 10.5181999, 'Германия', NULL, 'germanija', '1753', '1', '1768', 'COUNTRY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33581, 56.9462013, 24.1149998, 'Рига', 33561, 'riga', '1544', '2', '1545', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33583, 56.9487, 23.7068005, 'Юрмала', 33561, 'jurmala', '1546', '2', '1547', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33585, 36.5443993, 31.9953995, 'Аланья', 33541, 'alanja', '2198', '2', '2199', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33587, 18.9218998, -70.9832993, 'Доминиканская Республика', NULL, 'dominikanskaja-respublika', '1797', '1', '1802', 'COUNTRY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33589, 2, 33.6016998, 'Кипр', NULL, 'kipr', '2031', '1', '2058', 'COUNTRY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33591, 54.9892998, 73.3682022, 'Омск', 33517, 'omsk', '1590', '2', '1591', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33593, 43.1884003, 76.9679031, 'мкр Каменское плато', 89, 'almaty-medeuskij-kamenskoe-plato', '224', '4', '225', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33595, 45.0238991, 38.9701996, 'Краснодар', 33517, 'krasnodar', '1578', '2', '1579', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33597, 43.2360001, 76.938797, 'мкр Керемет', 73, 'almaty-bostandykskij-keremet', '188', '4', '189', 'MICRODISTRICT', '17');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33599, 43.3242989, 76.9166031, 'мкр Айнабулак-4', 83, 'almaty-zhetysuskij-ajnabulak-4', '210', '4', '211', 'MICRODISTRICT', '17');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33601, 30.9067993, 34.8496017, 'Израиль', NULL, 'izrail', '1823', '1', '1832', 'COUNTRY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33603, 31.7751999, 35.1988983, 'Иерусалим', 33601, 'ierusalim', '1826', '2', '1827', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33605, 32.0689011, 34.7941017, ' Тель-Авив', 33601, 'tel-aviv', '1828', '2', '1829', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33607, 32.8040009, 35.0051994, 'Хайфа', 33601, 'hajfa', '1830', '2', '1831', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33609, 54.7074013, 20.5072994, 'Калининград', 33517, 'kaliningrad', '1574', '2', '1575', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33611, 43.257, 76.8315964, 'мкр Акбулак', 29, 'almaty-aujezovskij-mkr-akbulak', '96', '4', '97', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33613, 50.2827988, 57.1907997, 'Старый город', 125, 'aktobe-staryj-gorod', '478', '4', '479', 'DISTRICT', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33615, 50.2872009, 57.1713982, 'Новый город', 125, 'aktobe-novyj-gorod', '474', '4', '477', 'DISTRICT', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33617, 49.7929001, 73.0726013, 'Казыбек би р-н', 239, 'karaganda-kazybek-bi', '1156', '4', '1157', 'DISTRICT', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33619, 49.7969017, 73.0973969, 'Октябрьский р-н', 239, 'karaganda-oktjabrskij', '1158', '4', '1159', 'DISTRICT', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33621, 42.2924995, 69.544899, 'Абайский р-н', 278, 'shymkent-abajskij', '1456', '4', '1457', 'DISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33623, 42.3227005, 69.6201019, 'Аль-Фарабийский р-н', 278, 'shymkent-al-farabijskij', '1458', '4', '1459', 'DISTRICT', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33625, 42.3125992, 69.631897, 'Енбекшинский р-н', 278, 'shymkent-enbekshinskij', '1460', '4', '1461', 'DISTRICT', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33627, 26.9148006, 30.2056999, 'Египет', NULL, 'egipet', '1803', '1', '1822', 'COUNTRY', '5');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33629, 27.8873997, 34.2896996, 'Шарм Эль Шейх', 33627, 'sharm-jel-shejh', '1818', '2', '1819', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33631, 55.030201, 82.9204025, 'Новосибирск', 33517, 'novosibirsk', '1600', '2', '1601', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33633, 40.3139992, 47.7322006, 'Азербайджан', NULL, 'azerbajdzhan', '1527', '1', '1530', 'COUNTRY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33635, 40.3931007, 49.8339996, 'Баку', 33633, 'baku', '1528', '2', '1529', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33637, 50.4500999, 30.5233994, 'Киев', 33559, 'kiev', '1656', '2', '1657', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33639, 44.4952011, 34.1663017, 'Ялта', 33559, 'yalta', '1660', '2', '1661', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33641, 46.4846001, 30.7325993, 'Одесса', 33559, 'odessa', '1658', '2', '1659', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33643, 52.1945992, 19.3512001, 'Польша', NULL, 'polska', '2133', '1', '2142', 'COUNTRY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33645, 52.2503014, 21.0711994, 'Варшава', 33643, 'varshava', '2134', '2', '2135', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33647, 50.0704002, 19.9666004, 'Краков', 33643, 'krakov', '2138', '2', '2139', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33649, 51.0993996, 17.0126991, 'Вроцлав', 33643, 'vroclav', '2136', '2', '2137', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33651, 44.2364998, 20.7717991, 'Сербия', NULL, 'serbia', '2153', '1', '2156', 'COUNTRY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33653, 44.6575012, 20.7113991, 'Белград', 33651, 'belgrad', '2154', '2', '2155', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33655, 40.0696983, -8.39694023, 'Португалия', NULL, 'portugalija', '2143', '1', '2146', 'COUNTRY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33657, 47.8013992, 14.6194, 'Австрия', NULL, 'avstrija', '1667', '1', '1680', 'COUNTRY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33659, 45.1400986, 16.4279995, 'Хорватия', NULL, 'horvatija', '2269', '1', '2278', 'COUNTRY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33661, 47.0295982, 8.0715704, 'Швейцария', NULL, 'shvejcarija', '2309', '1', '2316', 'COUNTRY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33663, 48.2123985, 16.3789005, 'Вена', 33657, 'vena', '1672', '2', '1673', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33665, 47.2666016, 12.7545996, 'Капрун', 33657, 'kaprun', '1676', '2', '1677', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33667, 47.8420982, 13.1857004, 'Зальцбург', 33657, 'zalcburg', '1674', '2', '1675', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33669, 47.6530991, 14.8062, 'Бадгаштайн', 33657, 'badgashtajn', '1670', '2', '1671', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33671, 42.6427002, 18.1105995, 'Дубровник', 33659, 'dubrovnik', '2270', '2', '2271', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33673, 46.6329002, 8.5947504, 'Андерматт', 33661, 'andermatt', '2310', '2', '2311', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33675, 37.2783012, -7.16008997, 'Алгарве', 33655, 'algarve', '2144', '2', '2145', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33677, 52.3170013, 16.1366997, 'Новы-Томысль', 33643, 'novy-tomysl', '2140', '2', '2141', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33679, 38.4301987, 27.1730003, 'Измир', 33541, 'izmir', '2206', '2', '2207', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33681, 57.1529999, 65.5343018, 'Тюмень', 33517, 'tjumen', '1632', '2', '1633', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33683, 42.5536995, 19.2653008, 'Черногория', NULL, 'сhernogorija', '2279', '1', '2302', 'COUNTRY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33685, 36.9211998, 30.7187004, 'Анталья', 33541, 'antalja', '2200', '2', '2201', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33687, 36.864399, 31.0713005, 'Белек', 33541, 'belek', '2202', '2', '2203', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33689, 36.6152992, 30.4906998, 'Кемер', 33541, 'kemer', '2208', '2', '2209', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33691, 41.0125008, 28.9134998, 'Стамбул', 33541, 'stambul', '2218', '2', '2219', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33693, 55.3816986, 23.4370003, 'Литва', NULL, 'litva', '1549', '1', '1550', 'COUNTRY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33695, 43.2126007, 76.8458023, 'мкр Мамыр-3', 29, 'almaty-aujezovskij-mkr mamyr-3', '142', '4', '143', 'MICRODISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33697, 43.2382011, 76.9539032, 'мкр Самал', 89, 'almaty-medeuskij-samal', '246', '4', '247', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33699, 43.2803001, 77.001297, 'мкр Думан-2', 89, 'almaty-medeuskij-duman-2', '242', '4', '243', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33701, 45.0444984, 41.969101, 'Ставрополь', 33517, 'сtavropol', '1626', '2', '1627', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33703, 50.5974998, 36.5887985, 'Белгород', 33517, 'belgorod', '1558', '2', '1559', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33705, 43.2126007, 76.8458023, 'мкр Мамыр-1', 29, 'almaty-aujezovskij-mkr-mamyr-1', '138', '4', '139', 'MICRODISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33707, 42.6604996, 27.7141991, 'Несебыр', 33533, 'nesebyr', '1702', '2', '1703', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33709, 42.7018013, 24.8560009, 'Златни-Пясыци', 33533, 'zlatni-pjasyci', '1696', '2', '1697', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33711, 42.4189987, 27.6938992, 'Созополь', 33533, 'sozopol', '1716', '2', '1717', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33713, 43.222599, 27.8980007, 'Варна', 33533, 'varna', '1692', '2', '1693', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33715, 42.5671997, 27.6187992, 'Поморие', 33533, 'pomorie', '1710', '2', '1711', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33717, 43.4651985, 25.7285004, 'Бяла', 33533, 'bjala', '1690', '2', '1691', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33719, 42.6899986, 27.7141991, 'Солнечный берег', 33533, 'solnechnyj-bereg', '1720', '2', '1721', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33721, 42.8175011, 27.8796005, 'Обзор', 33533, 'obzor', '1704', '2', '1705', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33723, 42.4985008, 27.4636002, 'Бургас', 33533, 'burgas', '1688', '2', '1689', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33725, 43.2316017, 28.0063992, 'Св. Константин и Елена', 33533, 'sv-кonstantin-i-еlena', '1714', '2', '1715', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33727, 40.1800995, -1.64077997, 'Санта-Крус-Де-Тенерифе', 33543, 'santa-rrus-de-tenerife', '1928', '2', '1929', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33729, 28.0538998, -16.7236996, 'Лос-Кристьянос', 33543, 'los-kristjanos', '1890', '2', '1891', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33731, 28.0470009, -16.5386009, 'Эль Медано', 33543, 'el-medano', '1954', '2', '1955', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33733, 46.3479004, 48.0335999, 'Астрахань', 33517, 'astrahan', '1554', '2', '1555', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33735, 12.9347, 100.902, 'Паттайя', 33539, 'pattajja', '2190', '2', '2191', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33737, 37.9757004, -0.680617988, 'Торревьеха', 33543, 'torreveha', '1944', '2', '1945', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33739, 39.5460014, -2.24569011, 'Аликанте', 33543, 'alikante', '1844', '2', '1845', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33741, 38.0867004, -0.654026985, 'Гвардамар-дель-Сегура', 33543, 'gvardamar-del-segura', '1870', '2', '1871', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33743, 0, 0, 'Пилар-де-ла-Орадада', 33543, 'pilar-de-la-oradada', '1910', '2', '1911', 'CITY', '0');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33745, 37.8340988, -0.793283999, 'Сан-Педро-дель-Пинатар', 33543, 'san-pedro-del-pinatar', '1930', '2', '1931', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33747, 38.5437012, -0.131827995, 'Бенидорм', 33543, 'benidorm', '1856', '2', '1857', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33749, 38.5057983, -0.232718006, 'Вильяхойоса', 33543, 'viljahojosa', '1868', '2', '1869', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33751, 38.5976982, -0.0485367998, 'Альтеа', 33543, 'altea', '1848', '2', '1849', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33753, 47.4919014, 19.0692997, 'Будапешт', 33537, 'budapesht', '1744', '2', '1745', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33755, 38.0154991, 23.7178993, 'Афины', 33545, 'afiny', '1770', '2', '1771', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33757, 41.8908005, 12.4959002, 'Рим', 33553, 'rim', '2008', '2', '2009', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33759, 50.0663986, 14.4466, 'Прага', 33523, 'praga', '2306', '2', '2307', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33761, 41.7650986, 63.1501007, 'Узбекистан', NULL, 'uzbekistan', '1663', '1', '1666', 'COUNTRY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33763, 41.3143005, 69.2673035, 'Ташкент', 33761, 'tashkent', '1664', '2', '1665', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33765, 38.4287987, -0.390758008, 'Кампельо', 33543, 'kampelo', '1880', '2', '1881', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33767, 36.8604012, -5.64418983, 'Вильямартин', 33543, 'viljamartin', '1866', '2', '1867', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33769, 37.8578987, 27.2609997, 'Кушадасы', 33541, 'kushadasy', '2214', '2', '2215', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33771, 21.2275009, 77.594101, 'Индия', NULL, 'indija', '1833', '1', '1836', 'COUNTRY', '5');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33773, 20.8096008, 78.5641022, 'Mурудешвара', 33771, 'murudeshvara', '1834', '2', '1835', 'CITY', '4');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33775, 45.4213982, -75.6975021, 'Оттава', 33555, 'ottava', '2028', '2', '2029', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33777, 51.5056, -0.142770007, 'Лондон', 33535, 'london', '1736', '2', '1737', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33779, 36.7839012, 31.4073009, 'Сиде', 33541, 'side', '2210', '2', '2211', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33781, 48.3445015, 13.7713003, 'Пойербах', 33657, 'pojerbah', '1678', '2', '1679', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33783, 7.95137978, 98.357399, 'Пхукет', 33539, 'phuket', '2192', '2', '2193', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33785, 39.0421982, 70.8481979, 'Таджикистан', NULL, 'tadzhikistan', '1651', '1', '1654', 'COUNTRY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33787, 38.5584984, 68.7633972, 'Душанбе', 33785, 'dushanbe', '1652', '2', '1653', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33789, 50.2284012, 12.8649998, 'Карловы Вары', 33523, 'karlovy-vary', '2304', '2', '2305', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33791, 24.4382, 54.4012985, 'Абу-Даби', 33551, 'abu-dabi', '2118', '2', '2119', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33793, 25.2388992, 55.3255997, 'Дубай', 33551, 'dubaj', '2122', '2', '2123', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33795, 44.9520988, 34.1024017, 'Симферополь', 33517, 'simferopol', '1618', '2', '1619', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33797, 41.9584999, 43.2607994, 'Грузия', NULL, 'gruzija', '1791', '1', '1796', 'COUNTRY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33799, 41.649601, 24.6872005, 'Пампорово', 33533, 'pamporovo', '1706', '2', '1707', 'CITY', '17');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33801, 41.4006004, 2.14396, 'Барселона', 33543, 'barselona', '1854', '2', '1855', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33803, 43.2724991, 6.63890982, 'Сен-Тропе', 33547, 'sen-trope', '2260', '2', '2261', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33805, 45.9884987, 4.71503019, 'Вильфранш-сюр-Мер', 33547, 'vilfransh-sjur-mer', '2236', '2', '2237', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33807, 43.7229004, 7.39775991, 'Кап-д''Айи ', 33547, 'kap-daji ', '2244', '2', '2245', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33809, 44.6755981, 3.90842009, 'Ла-Танья', 33547, 'la-tanja', '2250', '2', '2251', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33811, 45.4488983, 6.98028994, 'Валь-д''Изер', 33547, 'val-dizer', '2234', '2', '2235', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33813, 25.7747993, -80.1977005, 'Майами', 33557, 'majami', '2180', '2', '2181', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33815, 39.1899986, 106.818001, 'Аспен', 33557, 'aspen', '2178', '2', '2179', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33817, 29.1299992, -8.87092018, 'Марокко', NULL, 'marokko', '2103', '1', '2106', 'COUNTRY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33819, 31.6378002, -8.03238964, 'Марракеш', 33817, 'marrakesh', '2104', '2', '2105', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33821, 9.48429012, 99.9957962, 'Самуй', 33539, 'samuj', '2194', '2', '2195', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33823, 43.1889, 76.8766022, 'мкр Мирас', 73, 'almaty-bostandykskij-miras', '190', '4', '191', 'MICRODISTRICT', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33825, 50.8339996, 12.9125004, 'Хемниц', 33579, 'hemnic ', '1764', '2', '1765', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33827, 4.11650991, 114.001999, 'Малайзия', NULL, 'malajzija', '2089', '1', '2092', 'COUNTRY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33829, 3.14828992, 101.699997, 'Куала-Лумпур ', 33827, 'kuala-lumpur ', '2090', '2', '2091', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33831, 38.6362991, 127.874001, 'Корея', NULL, 'koreja', '2085', '1', '2088', 'COUNTRY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33833, 37.5620003, 126.982002, 'Сеул', 33831, 'seul', '2086', '2', '2087', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33835, 53.9019012, 27.5611992, 'Беларусь', NULL, 'belorussija', '1645', '1', '1650', 'COUNTRY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33837, 53.9061012, 27.5548992, 'Минск', 33835, 'minsk', '1648', '2', '1649', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33839, 52.9700012, 13.6964998, 'Берлин', 33579, 'berlin', '1756', '2', '1757', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33841, 52.1307983, 11.6281004, 'Магдебург', 33579, 'magdeburg', '1760', '2', '1761', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33843, 51.0572014, 13.7329998, 'Дрезден', 33579, 'drezden', '1758', '2', '1759', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33845, 48.1604996, 11.5745001, 'Мюнхен', 33579, 'mjunhen', '1762', '2', '1763', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33847, 53.4337997, 14.5243998, 'Штеттин', 33579, 'shtettin', '1766', '2', '1767', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33849, 45.8072014, 15.9675999, 'Загреб', 33659, 'zagreb', '2272', '2', '2273', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33851, 44.865799, 13.8494997, 'Пула', 33659, 'pula', '2274', '2', '2275', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33853, 45.3266983, 14.4471998, 'Риека', 33659, 'rieka', '2276', '2', '2277', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33855, 36.6557007, 29.1263008, 'Фетхие', 33541, 'fethie', '2212', '2', '2213', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33857, 37.0344009, 27.4305, 'Бодрум', 33541, 'bodrum', '2204', '2', '2205', 'CITY', '16');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33859, 42.6786995, 23.3398991, 'София', 33533, 'sofija', '1718', '2', '1719', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33861, 55.1603012, 61.4009018, 'Челябинск', 33517, 'cheljabinsk', '1638', '2', '1639', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33863, 42.5091019, 1.59872997, 'Княжество Андорра', NULL, 'knjazhestvo andorra', '2063', '1', '2078', 'COUNTRY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33865, 42.5082016, 1.52769005, 'Андорра-Ла-Велья', 33863, 'andorra-la-velya', '2064', '2', '2065', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33867, 42.487999, 1.58955002, 'Эскальдес-Энгордань', 33863, 'jeskaldjes-jengordan', '2076', '2', '2077', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33869, 42.5429993, 1.48319995, 'Ла-Массана', 33863, 'la-massana', '2068', '2', '2069', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33871, 42.5237999, 1.64476001, 'Энкамп', 33863, 'jenkamp', '2074', '2', '2075', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33873, 42.5779991, 1.67138004, 'Канильё', 33863, 'kaniljo', '2066', '2', '2067', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33875, 42.4668999, 1.49361002, 'Сан-Жулиа-де-Лория', 33863, 'san-zhulia-de-lorija', '2072', '2', '2073', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33877, 42.6062012, 1.54109001, 'Ордино', 33863, 'ordino', '2070', '2', '2071', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33879, 43.282299, 28.0832996, 'Лозенец', 33533, 'lozenec', '1700', '2', '1701', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33881, 46.2991982, 24.9102993, 'Румыния', NULL, 'rumynija', '2147', '1', '2150', 'COUNTRY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33883, 44.4399986, 26.1039009, 'Бухарест', 33881, 'buharest', '2148', '2', '2149', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33885, 38.5878983, -0.257167011, 'Кальп', 33543, 'kalp', '1878', '2', '1879', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33887, 39.2882004, -0.27364701, 'Бениса', 33543, 'benisa', '1858', '2', '1859', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33889, 40.1893997, -0.00448196987, 'Морайра', 33543, 'morajra', '1904', '2', '1905', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33891, 39.2989006, -1.14706004, 'Альбир', 33543, 'albir', '1846', '2', '1847', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33893, 39.2220001, -0.685634017, 'Ла Нусия', 33543, 'la-nusija', '1888', '2', '1889', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33895, 39.2561989, -1.26791, 'Финестрат', 33543, 'finestrat', '1950', '2', '1951', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33897, 39.1450996, -0.938319981, 'Полоп', 33543, 'polop', '1916', '2', '1917', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33899, 39.4098015, -1.52059996, 'Орчета', 33543, 'orcheta', '1908', '2', '1909', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33901, 41.594101, 1.01724994, 'Льорет-де-Мар', 33543, 'loret-de-mar', '1892', '2', '1893', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33903, 39.6310005, -1.39974999, 'Валенсия', 33543, 'valensija', '1864', '2', '1865', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33905, 39.5483017, -72.6691971, 'Нью-Йорк', 33557, 'new-jork', '2184', '2', '2185', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33907, 41.1078987, -0.0803970993, 'Плайя-де-Аро', 33543, 'plajja-de-aro', '1914', '2', '1915', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33909, 41.6880989, 2.26531005, 'Аргентона', 33543, 'argentona', '1850', '2', '1851', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33911, 43.2487984, 76.8528976, 'мкр Тастак-1', 29, 'lmaty-aujezovskij-mkr-tastak-1', '150', '4', '151', 'MICRODISTRICT', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33913, 25.4251995, 55.9081993, 'Рас-эль-Хайма', 33551, 'ras-al-hajma', '2124', '2', '2125', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33915, 25.6263008, 55.696701, 'Шарджа', 33551, 'shardzha', '2128', '2', '2129', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33917, 24.7173996, 54.0737, 'Аджман', 33551, 'adzhman', '2120', '2', '2121', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33919, 25.5869007, 55.5074005, 'Умм-эль-Кайвайн', 33551, 'umm-jel-kajvajn', '2126', '2', '2127', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33921, 24.6884995, 55.6963997, 'Эль-Фуджайра', 33551, 'jel-fudzhajra', '2130', '2', '2131', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33923, 38.766201, -0.747879028, 'Хавея', 33543, 'haveja', '1952', '2', '1953', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33925, 38.9458008, -0.372267991, 'Бенитачель', 33543, 'benitachel', '1860', '2', '1861', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33927, 43.3120003, 76.8110962, 'мкр Боралдай (Бурундай)', 3, 'boralday-vgorode', '6', '4', '7', 'MICRODISTRICT', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33929, 43.3224983, 76.938797, 'мкр Первомайское', 83, 'mkr-pervomajskoe', '202', '4', '203', 'MICRODISTRICT', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33931, 43.2122993, 76.9036026, 'мкр Алатау', 73, 'mkr-alatau', '168', '4', '169', 'MICRODISTRICT', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33933, 43.1940002, 76.9126968, 'мкр Ерменсай', 73, 'mkr-ermensaj', '170', '4', '171', 'MICRODISTRICT', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33935, 43.1780014, 76.9227982, 'мкр Нурлытау (Энергетик)', 73, 'mkr-nurlytau', '172', '4', '173', 'MICRODISTRICT', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33937, 43.1772995, 76.8347015, 'мкр Курамыс', 783, 'mkr-kuramys', '272', '4', '273', 'MICRODISTRICT', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33939, 43.1514015, 76.8644028, 'мкр Каргалы', 783, 'mkr-kargaly', '270', '4', '271', 'MICRODISTRICT', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33941, 43.1487999, 76.8551025, 'мкр Карагайлы', 783, 'mkr-karagajly', '268', '4', '269', 'MICRODISTRICT', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33943, 43.1646004, 76.8251038, 'мкр Жайлау', 783, 'mkr-zhajlau', '260', '4', '261', 'MICRODISTRICT', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33945, 43.1943016, 76.7833023, 'мкр Шугыла', 783, 'mkr-shugyla', '282', '4', '283', 'MICRODISTRICT', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33947, 43.1946983, 76.7913971, 'мкр Акжар', 783, 'mkr-akzhar', '258', '4', '259', 'MICRODISTRICT', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33949, 43.1729012, 76.816597, 'мкр Таусамалы', 783, 'mkr-tausamaly', '280', '4', '281', 'MICRODISTRICT', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33951, 43.1869011, 76.8337021, 'мкр Таужолы', 783, 'mkr-tauzholy', '278', '4', '279', 'MICRODISTRICT', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33953, 43.1749001, 76.8003998, 'мкр Тастыбулак', 783, 'mkr-tastybulak', '276', '4', '277', 'MICRODISTRICT', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33955, 43.1990013, 76.8348999, 'мкр Алгабас', 3, 'mkr-algabas', '4', '4', '5', 'MICRODISTRICT', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33957, 43.3134003, 76.8143997, 'мкр Теректы', 3, 'mkr-terekty', '12', '4', '13', 'MICRODISTRICT', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33959, 43.2857018, 76.8063965, 'мкр Рахат', 3, 'raxat-nizhnij', '10', '4', '11', 'MICRODISTRICT', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33961, 43.2974014, 76.8139038, 'мкр Мадениет', 3, 'mkr-madeniet', '8', '4', '9', 'MICRODISTRICT', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33963, 43.3569984, 76.9606018, 'мкр Альмерек', 99, 'mkr-almerek', '286', '4', '287', 'MICRODISTRICT', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33965, 43.3554001, 76.9833984, 'мкр Кайрат', 99, 'mkr-kajrat', '298', '4', '299', 'MICRODISTRICT', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33967, 43.3459015, 76.9776001, 'мкр Колхозши', 99, 'mkr-kolxozshi', '300', '4', '301', 'MICRODISTRICT', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33969, 43.2322006, 76.9023972, 'мкр Ремизовка', 73, 'mkr-remizovka', '174', '4', '175', 'MICRODISTRICT', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33971, 43.2070999, 76.9143982, 'мкр Актобе', 73, 'mkr-aktobe', '164', '4', '165', 'MICRODISTRICT', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33973, 43.1781998, 76.9532013, 'мкр Аккайын', 89, 'mkr-akkain', '220', '4', '221', 'MICRODISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33975, 43.2893982, 76.9772034, 'мкр Кольсай', 89, 'mkr-kolsaj', '226', '4', '227', 'MICRODISTRICT', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33977, 43.2677002, 76.9637985, 'мкр Сулусай', 89, 'mkr-sulusaj', '228', '4', '229', 'MICRODISTRICT', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33979, 43.1766014, 76.8340988, 'мкр Рахат', 783, 'mkr-raxat', '274', '4', '275', 'MICRODISTRICT', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33981, 52.5932007, 25.4596004, 'Брест', 33835, 'brest', '1646', '2', '1647', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33983, 43.1668015, 76.816597, 'мкр Калкаман-1', 783, 'kalkaman-1', '262', '4', '263', 'MICRODISTRICT', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33985, 43.1708984, 76.8182983, 'мкр Калкаман-2', 783, 'kalkaman-2', '264', '4', '265', 'MICRODISTRICT', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33987, 43.1721001, 76.8190002, 'мкр Калкаман-3', 783, 'kalkaman-3', '266', '4', '267', 'MICRODISTRICT', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33989, 44.8307991, 9.93542004, 'Милан', 33553, 'milan', '1994', '2', '1995', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33991, 44.946701, 12.1835003, 'Венеция', 33553, 'venecziya', '1978', '2', '1979', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33993, 43.7036018, 11.5164003, 'Тоскана', 33553, 'toskana', '2018', '2', '2019', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33995, 42.1529007, 12.9173002, 'Пиза', 33553, 'piza', '2002', '2', '2003', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33997, 43.2145004, 11.8003998, 'Перуджа', 33553, 'perudzha', '2004', '2', '2005', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (33999, 39.5284996, 3.16307998, 'Пальма-де-Майорка', 33543, 'palma-de-majorka', '1912', '2', '1913', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34001, 39.9301987, 4.09779978, 'Маон', 33543, 'maon', '1898', '2', '1899', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34003, 39.9119987, 3.98377991, 'Сиудадела', 33543, 'siudadela', '1922', '2', '1923', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34005, 39.050499, 1.48431003, 'Ивиса', 33543, 'ivisa', '1872', '2', '1873', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34007, 36.3504982, -5.13505983, 'Марбелья', 33543, 'marbelya', '1900', '2', '1901', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34009, 36.4860001, -4.95564985, 'Пуэрто-Банус', 33543, 'puerto-banus', '1920', '2', '1921', 'CITY', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34011, 37.5042, -2.16744995, 'Мурсия', 33543, 'mursiya', '1906', '2', '1907', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34013, 41.2071991, 1.44675004, 'Кома-Руга', 33543, 'koma-ruga', '1884', '2', '1885', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34015, 41.2475014, 1.80694997, 'Сиджес', 33543, 'sitzhes', '1924', '2', '1925', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34017, 42.3749008, 3.20437002, 'Кадакес', 33543, 'kadakes', '1874', '2', '1875', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34019, 37.9202995, -0.737882972, 'Плайя Фламенка', 33543, 'plajya-flamenka', '1918', '2', '1919', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34021, 41.6721001, 2.82209992, 'Бланес', 33543, 'blanes', '1862', '2', '1863', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34023, 41.8123016, 3.09661007, 'Багур', 33543, 'bagur', '1852', '2', '1853', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34025, 41.0722008, 1.04981995, 'Камбрильс', 33543, 'kambrils', '1876', '2', '1877', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34027, 41.7205009, 2.92725992, 'Тосса-де-Мар', 33543, 'tossa-de-mar', '1948', '2', '1949', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34029, 41.1453018, 1.26095998, 'Таррагона', 33543, 'tarragona', '1932', '2', '1933', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34267, 30.0039997, 31.2122002, 'Гиза', 33627, 'giza', '1806', '2', '1807', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34031, 41.2352982, 1.64472997, 'Торредембарра', 33543, 'torredembarra', '1946', '2', '1947', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34033, 42.2879982, 18.8400993, 'Будва', 33683, 'budva', '2286', '2', '2287', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34035, 42.1090012, 19.0582008, 'Бар', 33683, 'bar', '2282', '2', '2283', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34037, 42.1851997, 18.9179001, 'Сутоморе', 33683, 'sutomore', '2294', '2', '2295', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34039, 42.7282982, 19.2378006, 'Жабляк', 33683, 'zhablyak', '2292', '2', '2293', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34041, 42.5536995, 19.2653008, 'Добрые воды', 33683, 'dobrye-vody', '2288', '2', '2289', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34043, 42.5536995, 19.2653008, 'Утеха', 33683, 'utexa', '2300', '2', '2301', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34045, 42.5171013, 19.2598, 'Бечичи', 33683, 'bechichi', '2284', '2', '2285', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34047, 42.5469017, 19.0522003, 'Подгорица', 33683, 'podgoricza', '2290', '2', '2291', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34049, 42.1789017, 19.5345001, 'Петровац', 33683, 'petrovacz', '2280', '2', '2281', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34051, 51.6487007, 7.36477995, 'Нидерланды', NULL, 'niderlandy', '2107', '1', '2116', 'COUNTRY', '5');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34053, 51.530899, 5.46083021, 'Амстердам', 34051, 'amsterdam', '2108', '2', '2109', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34055, 51.5663986, 4.15596008, 'Гаага', 34051, 'gaaga', '2110', '2', '2111', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34057, 51.672699, 5.05694008, 'Утрехт', 34051, 'utrext', '2114', '2', '2115', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34059, 51.9441986, 4.74141979, 'Роттердам', 34051, 'rotterdam', '2112', '2', '2113', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34061, 41.3520012, 1.95947003, 'Кастельдефельс', 33543, 'kasteldefels', '1882', '2', '1883', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34063, 41.0764008, 1.12603998, 'Салоу', 33543, 'salou', '1926', '2', '1927', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34065, 43.6225014, 7.14845991, 'Антиб', 33547, 'antib', '2228', '2', '2229', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34067, 43.5629005, 7.31377983, 'Ницца', 33547, 'niczcza', '2254', '2', '2255', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34069, 39.6906013, 15.6534996, 'Скалея', 33553, 'skaleya', '2012', '2', '2013', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34071, 43.9230003, 9.99639034, 'Лидо-ди-Камайоре', 33553, 'lido-di-kamajore', '1990', '2', '1991', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34073, 42.6268005, 13.4186001, 'Пескара', 33553, 'peskara', '2006', '2', '2007', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34075, 45.5028992, 13.9808998, 'Триест', 33553, 'triest', '2020', '2', '2021', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34077, 43.6825981, 7.11994982, 'Босолей', 33547, 'bosolej', '2230', '2', '2231', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34079, 43.5261002, 6.32718992, 'Ла Коль-сюр-Лу', 33547, 'la-kol-syur-lu', '2246', '2', '2247', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34081, 43.7486992, 6.46049976, 'Вильнёв-Лубе', 33547, 'vilnyov-lube', '2232', '2', '2233', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34083, 45.4667015, 10.8866997, 'Верона', 33553, 'verona', '1980', '2', '1981', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34085, 43.5699005, 6.39909983, 'Канны', 33547, 'kanny', '2242', '2', '2243', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34087, 43.2237015, 6.01601982, 'Гольф-Жуан', 33547, 'golf-zhuan', '2238', '2', '2239', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34089, 43.0775986, 6.19556999, 'Рокебрюн-Кап-Мартен', 33547, '', '2256', '2', '2257', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34091, 54.7523003, 56.2985001, 'Уфа', 33517, 'ufa', '1634', '2', '1635', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34093, 43.7722015, 6.40844011, 'Ля Турби', 33547, 'lya-turbi', '2248', '2', '2249', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34095, 54.7639999, 68.6986008, 'Бишкуль', 264, 'bishkul', '1421', '3', '1422', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34097, 1.84287, 115.668999, 'Индонезия', NULL, 'indoneziya', '1837', '1', '1842', 'COUNTRY', '5');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34099, -7.51966, 113.945999, 'Денпасар', 34097, 'denpasar', '1838', '2', '1839', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34101, -9.52742958, 116.464996, 'Матарам', 34097, 'mataram', '1840', '2', '1841', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34103, 44.0228996, 9.54450989, 'Виареджо', 33553, 'viaredzho', '1984', '2', '1985', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34105, 43.3050003, 6.0869298, 'Фрежюс', 33547, 'frezhyus', '2264', '2', '2265', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34107, 43.7498016, 7.44868994, 'Эз', 33547, 'ez', '2226', '2', '2227', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34109, 53.5798988, 85.0115967, 'Барнаул', 33517, 'barnaul', '1556', '2', '1557', 'CITY', '5');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34111, 55.8056984, 66.7012024, 'Курган', 33517, 'kurgan', '1580', '2', '1581', 'CITY', '5');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34113, 54.7089996, 60.9492989, 'Екатеринбург', 33517, 'ekaterinburg', '1570', '2', '1571', 'CITY', '5');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34115, 55.2570992, 86.6074982, 'Томск', 33517, 'tomsk', '1630', '2', '1631', 'CITY', '5');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34117, 51.2338982, 51.4352989, 'Самара', 33517, 'samara', '1610', '2', '1611', 'CITY', '5');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34119, 49.5948982, 46.3195, 'Волгоград', 33517, 'volgograd', '1566', '2', '1567', 'CITY', '5');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34121, 53.3582001, 59.2019005, 'Магнитогорск ', 33517, 'magnitogorsk-', '1586', '2', '1587', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34123, 42.3582001, 69.5614014, 'Каратауский р-н', 278, 'karatauskij', '1462', '4', '1463', 'DISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34125, 65.5992966, 31.9601994, 'Финляндия', NULL, 'finlyandiya', '2221', '1', '2222', 'COUNTRY', '4');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34127, 18.5398006, -70.0274963, 'Санто-Доминго', 33587, 'santo-domingo', '1800', '2', '1801', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34129, 18.5991993, -68.8360977, 'Пунта-Кана', 33587, 'punta-kanta', '1798', '2', '1799', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34131, 51.9413986, 54.4421997, 'Оренбург', 33517, 'orenburg', '1592', '2', '1593', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34133, 35.0049019, 33.0864983, 'Ларнака', 33589, 'larnaka', '2038', '2', '2039', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34135, 35.0307999, 33.8890991, 'Айя-Напа', 33589, 'ajya-napa', '2032', '2', '2033', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34137, 35.1403999, 33.3203011, 'Лачи', 33589, 'lachi', '2040', '2', '2041', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34139, 34.7126999, 32.9087982, 'Лимассол', 33589, 'limassol', '2042', '2', '2043', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34141, 34.7974014, 32.8718987, 'Никосия', 33589, 'nikosiya', '2044', '2', '2045', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34143, 34.8378983, 33.0810013, 'Пафос', 33589, 'pafos', '2048', '2', '2049', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34145, 34.9939995, 33.9030991, 'Паралимни', 33589, 'paralimni', '2046', '2', '2047', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34147, 34.9566002, 33.114399, 'Полис', 33589, 'polis', '2050', '2', '2051', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34149, 35.2265015, 33.1455994, 'Протарас', 33589, 'protaras', '2052', '2', '2053', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34151, 56.0210991, 39.0758018, 'Владимир', 33517, 'vladimir', '1562', '2', '1563', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34153, 33.5559998, -117.516998, 'Лос-Анджелес', 33557, 'los-andzheles', '2182', '2', '2183', 'CITY', '5');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34155, 58.8305016, 67.2232971, 'Сургут', 33517, 'surgut', '1624', '2', '1625', 'CITY', '5');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34157, 42.5154991, 19.0767994, 'Тиват', 33683, 'tivat', '2296', '2', '2297', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34159, 53.0726013, -2.11385989, 'Манчестер', 33535, 'manchester', '1738', '2', '1739', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34161, 52.6203003, -3.27840996, 'Ливерпуль', 33535, 'liverpul', '1732', '2', '1733', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34163, 45.7559013, 15.1070995, 'Словения', NULL, 'sloveniya', '2157', '1', '2176', 'COUNTRY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34165, 45.8328018, 15.217, 'Любляна', 34163, 'lyublyana', '2160', '2', '2161', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34167, 45.6851997, 14.9575005, 'Община Мира-Костаневица', 34163, 'obshina-mira-kostanevicza', '2170', '2', '2171', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34169, 45.9297981, 16.3626003, 'Краньска-Гора', 34163, 'kranska-gora', '2158', '2', '2159', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34171, 45.8634987, 15.0632, 'Словень-Градец', 34163, 'sloven-gradecz', '2172', '2', '2173', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34173, 45.5555992, 15.6563997, 'Ново-Место', 34163, 'novo-mesto', '2168', '2', '2169', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34175, 46.1161995, 15.0960999, 'Менгеш', 34163, 'mengesh', '2166', '2', '2167', 'DISTRICT', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34177, 46.3297005, 15.0411997, 'Лютомер', 34163, 'lyutomer', '2162', '2', '2163', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34179, 46.1543999, 15.1730003, 'Марибор', 34163, 'maribor', '2164', '2', '2165', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34181, 46.1085014, 15.0851002, 'Целе', 34163, 'czele', '2174', '2', '2175', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34183, 42.7221985, 25.4538994, 'Банско', 33657, 'bansko', '1668', '2', '1669', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34185, 38.3031998, 22.6905994, 'Кавала', 33545, 'kavala', '1772', '2', '1773', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34187, 42.6337013, 13.6483002, 'Альба-Адриатика', 33553, 'alba-adriatika', '1966', '2', '1967', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34189, 42.0070992, 13.3965998, 'Л’Акуила', 33553, 'l’akuila', '1988', '2', '1989', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34191, 41.6394005, 13.0503998, 'Aсколи-Пичено', 33553, 'askoli-picheno', '1968', '2', '1969', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34193, 41.8265991, 13.9239998, 'Вилла-Роза', 33553, 'villa-roza', '1982', '2', '1983', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34195, 41.3978004, 13.7482004, 'Торторето', 33553, 'tortoreto', '2016', '2', '2017', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34197, 41.5554008, 14.6401997, 'Нерето', 33553, 'nereto', '1996', '2', '1997', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34199, 42.1599007, 14.3125, 'Терамо', 33553, 'teramo', '2014', '2', '2015', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34203, 35.3087006, 121.379997, 'Пекин', 34201, 'pekin', '2060', '2', '2061', 'CITY', '4');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34205, 27.0820999, 32.8204002, 'Хургада', 33627, 'xurgada', '1816', '2', '1817', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34207, 27.9011002, 31.4855003, 'Эль-Гуна', 33627, 'el-guna', '1820', '2', '1821', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34209, 28.0902996, 31.1504993, 'Марса-эль-Алам', 33627, 'marsa-el-alam', '1812', '2', '1813', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34211, 26.5202007, 32.9082985, 'Макади', 33627, 'makadi', '1810', '2', '1811', 'CITY', '5');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34213, 42.7252007, 27.4727993, 'Елените', 33533, 'elenite', '1698', '2', '1699', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34215, 44.638401, 41.6214981, 'Лермонтово', 33517, 'lermontovo', '1582', '2', '1583', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34217, 41.8025017, 43.5463982, 'Тбилиси', 33797, 'tbilisi', '1794', '2', '1795', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34219, 41.9421005, 42.0192986, 'Батуми', 33797, 'batumi', '1792', '2', '1793', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34221, 41.2717018, 73.4501038, 'Ош', 33511, 'osh', '1536', '2', '1537', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34223, 38.8681984, 23.0447998, 'Салоники', 33545, 'saloniki', '1782', '2', '1783', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34225, 39.1304016, 22.4165993, 'Патры', 33545, 'patry', '1780', '2', '1781', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34227, 38.8681984, 22.2758007, 'Лариса', 33545, 'larisa', '1778', '2', '1779', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34229, 39.1425018, 21.5286999, 'Ламия', 33545, 'lamiya', '1776', '2', '1777', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34231, 38.8853989, 22.4845009, 'Трикеа', 33545, 'trikea', '1784', '2', '1785', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34233, 39.0055008, 21.3484993, 'Янина', 33545, 'yanina', '1788', '2', '1789', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34235, 38.6100998, 23.2865009, 'Халкида', 33545, 'xalkida', '1786', '2', '1787', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34237, 37.769001, 22.8104, 'Каламата', 33545, 'kalamata', '1774', '2', '1775', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34239, 42.3063011, 25.2826004, 'Албена', 33533, 'albena', '1682', '2', '1683', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34241, 42.9311981, 25.6292992, 'Балчик', 33533, 'balchik', '1684', '2', '1685', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34243, 42.6885986, 25.6292992, 'Пловдив', 33533, 'plovdiv', '1708', '2', '1709', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34245, 42.7857018, 26.3544006, 'Добрич', 33533, 'dobrich', '1694', '2', '1695', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34247, 52.3027, 41.8188019, 'Воронеж', 33517, 'voronezh', '1564', '2', '1565', 'CITY', '5');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34249, 28.0116997, 31.7234001, 'Сахл-Хашиш', 33627, 'saxl-xashish', '1814', '2', '1815', 'CITY', '5');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34251, 42.4367981, 25.0799999, 'Черноморец', 33533, 'chernomorecz', '1722', '2', '1723', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34253, 35.0834007, 33.4622993, 'Фамагуста', 33589, 'famagusta', '2056', '2', '2057', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34255, 34.709301, 32.7433014, 'Искеле', 33589, 'iskele', '2036', '2', '2037', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34257, 37.6161995, 33.1817017, 'Гирне', 33589, 'girne', '2034', '2', '2035', 'CITY', '5');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34259, 51.5481987, 46.0509987, 'Саратов', 33517, 'saratov', '1612', '2', '1613', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34261, 43.2459984, 76.8149033, 'Аккент', 3, 'akkent', '24', '4', '25', 'MICRODISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34263, 30.0499992, 31.2504997, 'Каир', 33627, 'cairo', '1808', '2', '1809', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34265, 31.156601, 29.8831005, 'Александрия', 33627, 'alexandria', '1804', '2', '1805', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34269, 43.4192009, 9.67144012, 'Сан-Ремо', 33553, 'san-remo', '2010', '2', '2011', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34271, 43.5447006, 10.9575996, 'Алассио', 33553, 'alassio', '1962', '2', '1963', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34273, 43.331501, 8.25730038, 'Андора', 33553, 'andora', '1964', '2', '1965', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34275, 42.7547989, 11.0725002, 'Берджеджи', 33553, 'berdzhedzhi', '1970', '2', '1971', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34277, 43.2402992, 12.8253002, 'Оспедалетти', 33553, 'ospedaletti', '1998', '2', '1999', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34279, 43.2526016, 12.7585001, 'Бордигера', 33553, 'bordigera', '1972', '2', '1973', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34281, 43.5126991, 11.8584995, 'Генуя', 33553, 'genuya', '1974', '2', '1975', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34283, 36.7645988, 15.3632002, 'Палермо', 33553, 'palermo', '2000', '2', '2001', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34285, 43.2518005, 10.6969004, 'Флоренция', 33553, 'florencziya', '2022', '2', '2023', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34287, 43.3540993, 8.96111012, 'Лукка', 33553, 'lukka', '1992', '2', '1993', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34289, 43.2080994, 12.0783005, 'Форте-деи-Марми', 33553, 'forte-dei-marmi', '2024', '2', '2025', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34291, 43.6086006, 11.1554003, 'Варезе', 33553, 'vareze', '1976', '2', '1977', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34293, 43.8316994, 11.7925997, 'Комо', 33553, 'komo', '1986', '2', '1987', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34295, 42.6282997, 10.5038996, 'Княжество Монако', NULL, 'knyazhestvo-monako', '2079', '1', '2084', 'COUNTRY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34297, 42.4658012, 9.91063976, 'Монако', 34295, 'monako', '2080', '2', '2081', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34299, 42.7579994, 10.1084003, 'Монте-Карло', 34295, 'monte-karlo', '2082', '2', '2083', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34301, 41.7999001, 25.6889992, 'Банско', 33533, 'balsko', '1686', '2', '1687', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34303, 49.8658981, 9.28345013, 'Баден-Баден', 33579, 'baden-baden', '1754', '2', '1755', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34305, 42.5536995, 19.2653008, 'Херцег-Нови', 33683, 'xerczeg-novi', '2298', '2', '2299', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34307, 35.8470993, 15.4868002, 'Мальта', NULL, 'malta', '2093', '1', '2102', 'COUNTRY', '5');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34309, 35.8470993, 15.4868002, 'Валетта', 34307, 'valetta', '2094', '2', '2095', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34311, 44.4515991, 34.4963989, 'Ялта', 33517, 'yaltas', '1640', '2', '1641', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34313, 45.4962997, 34.9994011, 'Евпатория', 33517, 'evpatoriya', '1568', '2', '1569', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34315, 43.3637009, 34.4724007, 'Севастополь', 33517, 'sevastopol', '1620', '2', '1621', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34317, 35.8760986, 14.4793997, 'Слима', 34307, 'slima', '2100', '2', '2101', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34319, 35.8694992, 14.4605999, 'Сент-Джулианс', 34307, 'sent-dzhulians', '2098', '2', '2099', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34321, 35.8927002, 14.4186001, 'Меллиеха', 34307, 'melliexa', '2096', '2', '2097', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34323, 57.8207016, 28.4699993, 'Струги Красные', 33517, 'strugi-krasnye', '1628', '2', '1629', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34325, 44.8778992, 37.3025017, 'Анапа', 33517, 'anapa', '1552', '2', '1553', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34327, 46.9920006, 8.22537994, 'Лугано', 33661, 'lugano', '2314', '2', '2315', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34329, 46.5432014, 7.14808989, 'Женева', 33661, 'zheneva', '2312', '2', '2313', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34331, 47.0351982, 2.69814992, 'Эвиан-ле-Бен ', 33547, 'evian-le-ben', '2266', '2', '2267', 'CITY', '5');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34335, 47.0351982, 2.69814992, 'Толлон-ле-Мемиз', 33547, 'tollon-le-memiz', '2262', '2', '2263', 'CITY', '5');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34337, 47.4687996, 6.19312, 'Морзин', 33547, 'morzin', '2252', '2', '2253', 'CITY', '5');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34339, 43.1725006, 76.8951035, 'мкр Хан Тенгри', 73, 'mkr-han-tengri', '166', '4', '167', 'MICRODISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34341, 40.0544014, -4.26717997, 'Эстепона', 33543, 'estepona', '1958', '2', '1959', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34343, 31.3085003, 35.4263992, 'Ашдод', 33601, 'ashdod', '1824', '2', '1825', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34345, 48.8661995, 2.33724999, 'Париж', 33547, 'paris', '2224', '2', '2225', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34347, 48.8978004, 2.09267998, 'Сен-Жермен-ан-Ле', 33547, 'saint-germain-en-laye', '2258', '2', '2259', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34349, 43.3987007, 77.0264969, 'Жана Куат', 132, 'zhana-kuat', '665', '3', '666', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34351, 28.1256008, -81.8239975, 'Орландо', 33557, 'orlando', '2186', '2', '2187', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34353, 36.3633003, -5.51959991, 'Манильва', 33543, 'manilva', '1896', '2', '1897', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34355, 45.1286011, 34.4313011, 'Феодосия', 33517, 'feodosiya', '1636', '2', '1637', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34357, 47.236599, 40.2169991, 'Ростов-на-дону', 33517, 'rostov-na-donu', '1608', '2', '1609', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34359, 43.2584, 77.0007019, 'Кенсай', 89, 'kensai', '240', '4', '241', 'DISTRICT', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34361, 43.3398018, 76.9835968, 'Жас Канат', 99, 'zhas-kanat', '296', '4', '297', 'MICRODISTRICT', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34363, 55.6039009, 37.9407005, 'Реутов', 33517, 'reutov', '1606', '2', '1607', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34365, 42.4664993, 77.1139984, 'Бостери', 33511, 'bosteri', '1534', '2', '1535', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34367, 38.8087006, -3.44989991, 'Марина д''Ор', 33543, 'marina-d''or', '1902', '2', '1903', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34369, 36.7587013, -4.45124006, 'Малага', 33543, 'malaga', '1894', '2', '1895', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34371, 43.4724998, 77.4040985, 'Ташкенсаз', 132, 'tashkensaz', '853', '3', '854', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34373, 53.8218002, -1.99425006, 'Лидс', 33535, 'lids', '1734', '2', '1735', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34375, 54.7527008, -2.11629009, 'НьюКасл-апон-Тайн', 33535, 'nyukasl-apon-tajn', '1740', '2', '1741', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34377, 38.1943016, -0.693769991, 'Эльче', 33543, 'elche', '1956', '2', '1957', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34379, 43.342701, 77.2040024, 'Туганбай', 132, 'tuganbaj', '869', '3', '870', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34381, 51.1679001, 58.537899, 'Орск', 33517, 'orsk', '1594', '2', '1595', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34383, 50.9429016, 71.0250015, 'Отемис', 112, 'otemis', '423', '3', '424', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34505, 47, 72, 'Шет', 235, 'shet', '1153', '3', '1154', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34385, 51.0293999, 71.7629013, 'Жалтырколь', 112, 'zhaltyrkol', '361', '3', '362', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34387, 45.471199, 131.542007, 'Владивосток', 33517, 'vladivostok', '1560', '2', '1561', 'CITY', '5');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34389, 57.1138, 24.1390991, 'Даугавпилс', 33561, 'daugavpils', '1542', '2', '1543', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34391, 63.0611, 7.7363801, 'Швеция', NULL, 'shvecziya', '2317', '1', '2324', 'COUNTRY', '4');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34393, 60.955101, 11.0761995, 'Осло', 34391, 'oslo', '2318', '2', '2319', 'CITY', '4');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34395, 60.5465012, 12.8339996, 'Стокгольм', 34391, 'stokgolm', '2320', '2', '2321', 'CITY', '5');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34397, 59.9841995, 17.0653992, 'Эстерсунд', 34391, 'estersund', '2322', '2', '2323', 'CITY', '5');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34399, 54.3488998, 72.411499, 'Привальное', 33517, 'privalnoe', '1604', '2', '1605', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34401, 50.4051018, 50.4082985, 'Щапово', 232, 'shapovo', '1149', '3', '1150', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34403, 0, 0, 'Коста-Бланка', 33543, 'kosta-blanka', '1886', '2', '1887', 'CITY', '0');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34405, 51.6599998, 70.9891968, 'Кызылжар', 112, 'kyzylzhar', '411', '3', '412', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34407, 44.9817009, 78.0864029, 'Жанакурылыс', 132, 'zhanakurylys', '661', '3', '662', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34409, 43.2627983, 77.2717972, 'Рыскулово', 132, 'ryskulovo', '833', '3', '834', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34411, 52.0275002, 70.3958969, 'Ольгинка', 112, 'olginka', '421', '3', '422', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34413, 43.9720993, 77.4150009, 'Куйган', 132, 'kujgan', '779', '3', '780', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34415, 52.4860001, 72.1756973, 'Гранитный', 112, 'granitnyj', '341', '3', '342', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34417, 43.7056007, 76.3896027, 'Сауыншы', 132, 'sauynshy', '839', '3', '840', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34419, 43.3622017, 45.5858994, 'Назрань', 33517, 'nazran', '1596', '2', '1597', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34421, 54.8470993, 20.2185001, 'Светлогорск', 33517, 'Svetlogorsk', '1616', '2', '1617', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34423, 54.8218002, 20.4321003, 'Зеленоградск', 33517, 'Zelenogradsk', '1572', '2', '1573', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34425, 54.8456993, 20.0135994, 'Янтарный', 33517, 'Yantarniy', '1642', '2', '1643', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34427, 54.8909988, 20.2287998, 'Пионерский', 33517, 'pionerskij', '1602', '2', '1603', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34429, 53.1511993, 40.5252991, 'Липецк', 33517, 'lipeczk', '1584', '2', '1585', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34431, 43.8319016, 76.976799, 'Теректы', 132, 'terekty', '857', '3', '858', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34433, 43.2551003, 69.3082962, 'Коксаек', 270, 'koksaek', '1491', '3', '1492', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34435, 43.3148994, 76.5233002, 'Кумарал', 132, 'kumaral', '781', '3', '782', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34437, 59.4724998, 47.7276001, 'Новокузнецк', 33517, 'novokuzneczk', '1598', '2', '1599', 'CITY', '4');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34439, 51.2005005, 71.3522034, 'Тонкерис', 112, 'tonkeris1', '451', '3', '452', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34441, 50.7519989, 72.5437012, 'Ключевое', 112, 'klyuchevoe', '387', '3', '388', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34443, 49.5411987, 57.3983002, 'Кос-Истек', 124, 'kos-istek', '495', '3', '496', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34445, 43.5138016, 77.1009979, 'Тузусай', 132, 'tuzusaj', '873', '3', '874', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34447, 52.0381012, 71.3980026, 'Тастак', 112, 'tastak', '447', '3', '448', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34449, 45.5866013, 78.2401962, 'Кызылжар', 132, 'kyzylzhar3', '787', '3', '788', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34451, 51.5168991, 71.680397, 'Талкара', 112, 'talkara', '443', '3', '444', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34453, 45.0234985, 78.1846008, 'Отенай', 132, 'otenaj', '825', '3', '826', 'CITY', '8');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34455, 0, 0, 'Садовое', 112, 'sadovoe1', '433', '3', '434', 'CITY', '0');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34457, 44.2094994, 70.7882996, 'Аса', 227, 'asa', '1065', '3', '1066', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34459, 45.3513985, 36.4752998, 'Керч', 33517, 'kerch', '1576', '2', '1577', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34461, 49.6343002, 57.289299, 'Каракудук', 124, 'karakuduk', '493', '3', '494', 'CITY', '7');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34463, 16.2887001, 107.803001, 'Вьетнам', NULL, 'vetnam', '1747', '1', '1752', 'COUNTRY', '5');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34465, 18.6713009, 106.835999, 'Ханой', 34463, 'xanoj', '1748', '2', '1749', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34467, 14.8184996, 108.710999, 'Нячанг', 34463, 'nyachang', '1750', '2', '1751', 'CITY', '6');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34469, 51.6293983, 69.8367004, 'Жалтыр', 112, 'zhaltyr', '359', '3', '360', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34475, 49.9384003, 82.5117035, 'п. Ново-Ульбинка', 216, 'novoulbinka', '1019', '3', '1020', 'CITY', '9');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34477, 43.1694984, 76.3350983, 'Енбекшиарал', 132, 'enbekshiaral', '535', '3', '536', 'CITY', '11');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34481, 50.4281006, 73.2497025, 'Мирное', 235, 'mirnoe', '1193', '3', '1194', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34483, 50.2882996, 57.0724983, 'Новый', 124, 'noviy', '507', '3', '508', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34485, 51.270401, 51.3763008, 'Зеленое', 232, 'zelenoe', '1123', '3', '1124', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34487, 53.0852013, 64.251503, 'Силантьевка', 247, 'silantevka', '1293', '3', '1294', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34489, 53.3092003, 66.0701981, 'Комсомольское', 247, 'komsomolskoe', '1233', '3', '1234', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34491, 44.3595009, 78.5529022, 'Когалы', 132, 'kogaly', '741', '3', '742', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34493, 49.9845009, 82.5639038, 'Мирный', 224, 'mirnyj', '966', '4', '967', 'DISTRICT', '15');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34495, 54.5898018, 67.1507034, 'Пресновка', 264, 'presnovka', '1441', '3', '1442', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34497, 51.2781982, 51.4175987, 'Садовое ', 232, 'sadovoe-', '1141', '3', '1142', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34499, 51.0886002, 51.5331993, 'Новая жизнь', 232, 'novaya-zhizn', '1129', '3', '1130', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34501, 43.6227989, 51.2075996, 'Приморский', 257, 'primorskij', '1361', '3', '1362', 'CITY', '14');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34503, 43.2466011, 76.356102, 'Умбетали', 132, 'umbetali', '891', '3', '892', 'CITY', '13');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34511, 49, 81, 'Степное', 216, 'stepnoe', '969', '3', '970', 'CITY', '0');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34513, 42, 71, 'Бурыл', 227, 'buryl', '1057', '3', '1058', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34523, 52, 63, 'Рыспай', 247, 'ryspaj', '1231', '3', '1232', 'CITY', '0');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34527, 53.3247986, 75.4708023, 'Иртышск', 260, 'irtyshsk', '1383', '3', '1384', 'CITY', '12');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34509, NULL, NULL, 'Киевский', 247, 'kievskij', '1271', '3', '1272', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34507, NULL, NULL, 'Пригородный', 291, 'prigorodnyj_esil', '308', '4', '309', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34515, NULL, NULL, 'Акжар', 124, 'akzhar', '471', '3', '472', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34517, NULL, NULL, 'Тюра-там', 885, 'tyura-tam', '1314', '4', '1315', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34519, NULL, NULL, 'Туймебая', 142, 'tujmebaya', '590', '4', '591', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34521, NULL, NULL, 'Акжар2', 33615, 'akzhar2', '475', '5', '476', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34525, NULL, NULL, 'Нижний Курайлы', 124, 'nizhnij-kurajly', '469', '3', '470', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34479, NULL, NULL, 'Костомар', 112, 'kostomar', '397', '3', '398', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34473, NULL, NULL, 'Комсомольский', 107, 'komsomolskij', '314', '4', '315', 'CITY', '10');
INSERT INTO region (id, latitude, longitude, name, parent_id, alias, leftkey, level, rightkey, type, zoom) VALUES (34471, NULL, NULL, 'Дзержинец', 222, 'dzerzhinecz', '1032', '4', '1033', 'COUNTRY', '10');


--
-- TOC entry 2376 (class 0 OID 87095)
-- Dependencies: 186
-- Data for Name: region_irr; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO region_irr (id, key, name, regionid_id) VALUES (9, 'kazahstan/kyzylordinskaya-obl/', 'Кызылординская обл.', 253);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (10, 'kazahstan/mangistauskaya-obl/', 'Мангыстауская обл.', 257);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (33, 'kazahstan/almatinskaya-obl/almaty-gorod/', 'Алмата', 2);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (68, 'kazahstan/karagandinskaya-obl/stapaev-gorod/', 'Сатпаев', 244);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (77, 'kazahstan/kyzylordinskaya-obl/baykonur-gorod/', 'Байконур', 885);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (96, 'kazahstan/yuzhno-kazahstanskaya-obl/shardara-gorod/', 'Шардара', 277);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (36, 'kazahstan/almatinskaya-obl/kapchagay-gorod/', 'Капчагай', 2000);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (15, 'kazahstan/akmolinskaya-obl/akkol-gorod/', 'Акколь', 113);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (16, 'kazahstan/akmolinskaya-obl/astana-gorod/', 'Астана', 105);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (17, 'kazahstan/akmolinskaya-obl/atbasar-gorod/', 'Атбасар', 114);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (18, 'kazahstan/akmolinskaya-obl/derzhavinsk-gorod/', 'Державинск', 116);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (19, 'kazahstan/akmolinskaya-obl/ereymentau-gorod/', 'Ерейментау', 117);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (1, 'kazahstan/akmolinskaya-obl/', 'Акмолинская обл.', 112);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (2, 'kazahstan/aktyubinskaya-obl/', 'Актюбинская обл.', 124);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (3, 'kazahstan/almatinskaya-obl/', 'Алматинская обл.', 132);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (4, 'kazahstan/atyrauskaya-obl/', 'Атырауская обл.', 213);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (5, 'kazahstan/vostochno-kazahstanskaya-obl/', 'Восточно-Казахстанская обл.', 216);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (6, 'kazahstan/zhambylskaya-obl/', 'Жамбылская обл.', 227);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (7, 'kazahstan/karagandinskaya-obl/', 'Карагандинская обл.', 235);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (8, 'kazahstan/kostanayskaya-obl/', 'Костанайская обл.', 247);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (11, 'kazahstan/pavlodarskaya-obl/', 'Павлодарская обл.', 260);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (12, 'kazahstan/severo-kazahstanskaya-obl/', 'Северо-Казахстанская обл.', 264);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (13, 'kazahstan/yuzhno-kazahstanskaya-obl/', 'Южно-Казахстанская обл.', 270);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (14, 'kazahstan/zapadno-kazahstanskaya-obl/', 'Западно-Казахстанская обл.', 232);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (20, 'kazahstan/akmolinskaya-obl/esil-gorod/', 'Есиль', 118);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (21, 'kazahstan/akmolinskaya-obl/kokshetau-gorod/', 'Кокшетау', 119);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (22, 'kazahstan/akmolinskaya-obl/makinsk-gorod/', 'Макинск', 120);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (23, 'kazahstan/akmolinskaya-obl/stepnogorsk-gorod/', 'Степногорск', 121);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (24, 'kazahstan/akmolinskaya-obl/stepnyak-gorod/', 'Степняк', 122);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (25, 'kazahstan/akmolinskaya-obl/schuchinsk-gorod/', 'Щучинск', 123);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (26, 'kazahstan/aktyubinskaya-obl/aktobe-gorod/', 'Актобе', 125);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (27, 'kazahstan/aktyubinskaya-obl/alga-gorod/', 'Алга', 1501);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (28, 'kazahstan/aktyubinskaya-obl/kandyagash-gorod/', 'Кандыагаш', 127);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (29, 'kazahstan/aktyubinskaya-obl/temir-gorod/', 'Темир', 128);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (30, 'kazahstan/aktyubinskaya-obl/hromtau-gorod/', 'Хромтау', 129);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (31, 'kazahstan/aktyubinskaya-obl/shalkar-gorod/', 'Шалкар', 130);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (32, 'kazahstan/aktyubinskaya-obl/emba-gorod/', 'Эмба', 131);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (34, 'kazahstan/almatinskaya-obl/esik-gorod/', 'Есик', 156);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (35, 'kazahstan/almatinskaya-obl/zharkent-gorod/', 'Жаркент', 160);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (37, 'kazahstan/almatinskaya-obl/kaskelen-gorod/', 'Каскелен', 172);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (38, 'kazahstan/almatinskaya-obl/sarkand-gorod/', 'Сарканд', 193);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (39, 'kazahstan/almatinskaya-obl/talgar-gorod/', 'Талгар', 194);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (40, 'kazahstan/almatinskaya-obl/taldykorgan-gorod/', 'Талдыкорган', 195);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (41, 'kazahstan/almatinskaya-obl/tekeli-gorod/', 'Текели', 197);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (42, 'kazahstan/almatinskaya-obl/usharal-gorod/', 'Ушарал', 202);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (43, 'kazahstan/almatinskaya-obl/ushtobe-gorod/', 'Уштобе', 1745);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (44, 'kazahstan/atyrauskaya-obl/atyrau-gorod/', 'Атырау', 214);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (45, 'kazahstan/atyrauskaya-obl/kulsary-gorod/', 'Кульсары', 215);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (46, 'kazahstan/vostochno-kazahstanskaya-obl/ayagoz-gorod/', 'Аягоз', 217);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (47, 'kazahstan/vostochno-kazahstanskaya-obl/zaysan-gorod/', 'Зайсан', 218);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (48, 'kazahstan/vostochno-kazahstanskaya-obl/zyryanovsk-gorod/', 'Зыряновск', 219);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (49, 'kazahstan/vostochno-kazahstanskaya-obl/kurchatov-gorod/', 'Курчатов', 220);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (50, 'kazahstan/vostochno-kazahstanskaya-obl/ridder-gorod/', 'Риддер', 221);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (51, 'kazahstan/vostochno-kazahstanskaya-obl/semey-gorod/', 'Семей', 222);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (52, 'kazahstan/vostochno-kazahstanskaya-obl/serebryansk-gorod/', 'Серебрянск', 223);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (53, 'kazahstan/vostochno-kazahstanskaya-obl/ust-kamenogorsk-gorod/', 'Усть-Каменогорск', 224);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (54, 'kazahstan/vostochno-kazahstanskaya-obl/shar-gorod/', 'Шар', 225);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (55, 'kazahstan/vostochno-kazahstanskaya-obl/shemonaiha-gorod/', 'Шемонаиха', 226);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (56, 'kazahstan/zhambylskaya-obl/zhanatas-gorod/', 'Жанатас', 228);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (57, 'kazahstan/zhambylskaya-obl/karatau-gorod/', 'Каратау', 229);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (58, 'kazahstan/zhambylskaya-obl/taraz-gorod/', 'Тараз', 230);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (59, 'kazahstan/zhambylskaya-obl/shu-gorod/', 'Шу', 231);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (60, 'kazahstan/karagandinskaya-obl/abay-gorod/', 'Абай', 236);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (61, 'kazahstan/karagandinskaya-obl/balhash-gorod/', 'Балхаш', 237);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (62, 'kazahstan/karagandinskaya-obl/zhezkazgan-gorod/', 'Жезказган', 238);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (63, 'kazahstan/karagandinskaya-obl/karaganda-gorod/', 'Караганда', 239);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (64, 'kazahstan/karagandinskaya-obl/karazhal-gorod/', 'Каражал', 240);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (65, 'kazahstan/karagandinskaya-obl/karkaralinsk-gorod/', 'Каркаралинск', 241);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (67, 'kazahstan/karagandinskaya-obl/saran-gorod/', 'Сарань', 243);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (66, 'kazahstan/karagandinskaya-obl/priozyorsk-gorod/', 'Приозерск', 242);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (69, 'kazahstan/karagandinskaya-obl/temirtau-gorod/', 'Темиртау', 245);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (70, 'kazahstan/karagandinskaya-obl/shahtinsk-gorod/', 'Шахтинск', 246);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (71, 'kazahstan/kostanayskaya-obl/arkalyk-gorod/', 'Аркалык', 248);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (72, 'kazahstan/kostanayskaya-obl/zhitikara-gorod/', 'Житикара', 249);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (73, 'kazahstan/kostanayskaya-obl/kostanay-gorod/', 'Костанай', 250);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (74, 'kazahstan/kostanayskaya-obl/lisakovsk-gorod/', 'Лисаковск', 251);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (75, 'kazahstan/kostanayskaya-obl/rudnyy-gorod/', 'Рудный', 252);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (76, 'kazahstan/kyzylordinskaya-obl/aralsk-gorod/', 'Аральск', 254);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (78, 'kazahstan/kyzylordinskaya-obl/kazalinsk-gorod/', 'Казалинск', 255);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (79, 'kazahstan/kyzylordinskaya-obl/kyzylorda-gorod/', 'Кызылорда', 256);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (80, 'kazahstan/mangistauskaya-obl/aktau-gorod/', 'Актау', 258);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (81, 'kazahstan/mangistauskaya-obl/zhanaozen-gorod/', 'Жанаозен', 259);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (82, 'kazahstan/pavlodarskaya-obl/aksu-gorod/', 'Аксу', 261);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (83, 'kazahstan/pavlodarskaya-obl/pavlodar-gorod/', 'Павлодар', 262);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (84, 'kazahstan/pavlodarskaya-obl/ekibastuz-gorod/', 'Экибастуз', 263);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (85, 'kazahstan/severo-kazahstanskaya-obl/bulaevo-gorod/', 'Булаево', 265);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (86, 'kazahstan/severo-kazahstanskaya-obl/mamlyutka-gorod/', 'Мамлютка', 266);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (87, 'kazahstan/severo-kazahstanskaya-obl/petropavlovsk-gorod/', 'Петропавловск', 267);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (88, 'kazahstan/severo-kazahstanskaya-obl/sergeevka-gorod/', 'Сергеевка', 1317);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (89, 'kazahstan/severo-kazahstanskaya-obl/tayynsha-gorod/', 'Тайынша', 269);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (90, 'kazahstan/yuzhno-kazahstanskaya-obl/arys-gorod/', 'Арысь', 271);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (91, 'kazahstan/yuzhno-kazahstanskaya-obl/zhetysay-gorod/', 'Жетысай', 272);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (92, 'kazahstan/yuzhno-kazahstanskaya-obl/kentau-gorod/', 'Кентау', 273);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (93, 'kazahstan/yuzhno-kazahstanskaya-obl/lenger-gorod/', 'Ленгер', 274);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (94, 'kazahstan/yuzhno-kazahstanskaya-obl/saryagash-gorod/', 'Сарыагаш', 275);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (95, 'kazahstan/yuzhno-kazahstanskaya-obl/turkestan-gorod/', 'Туркестан', 276);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (97, 'kazahstan/yuzhno-kazahstanskaya-obl/shymkent-gorod/', 'Шымкент', 278);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (99, 'kazahstan/zapadno-kazahstanskaya-obl/uralsk-gorod/', 'Уральск', 234);
INSERT INTO region_irr (id, key, name, regionid_id) VALUES (98, 'kazahstan/zapadno-kazahstanskaya-obl/aksay-gorod/', 'Аксай', 233);


--
-- TOC entry 2377 (class 0 OID 87101)
-- Dependencies: 187
-- Data for Name: region_kn; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO region_kn (id, key, name, region_id) VALUES (1, 'almaty', 'Алматы', 2);
INSERT INTO region_kn (id, key, name, region_id) VALUES (2, 'astana', 'Астана', 105);
INSERT INTO region_kn (id, key, name, region_id) VALUES (3, 'karaganda', 'Караганда', 239);
INSERT INTO region_kn (id, key, name, region_id) VALUES (4, 'ust-kamenogorsk', 'Усть-Каменогорск', 224);
INSERT INTO region_kn (id, key, name, region_id) VALUES (5, 'uralsk', 'Уральск', 234);
INSERT INTO region_kn (id, key, name, region_id) VALUES (6, 'atyrau', 'Атырау', 214);
INSERT INTO region_kn (id, key, name, region_id) VALUES (7, 'aktau', 'Актау', 258);
INSERT INTO region_kn (id, key, name, region_id) VALUES (8, 'abay', 'Абай', 236);
INSERT INTO region_kn (id, key, name, region_id) VALUES (53, 'kulsary', 'Кульсары', 215);
INSERT INTO region_kn (id, key, name, region_id) VALUES (9, 'abay-almatinskaya-oblast', 'Алматинская обл.', 132);
INSERT INTO region_kn (id, key, name, region_id) VALUES (10, 'akkol', 'Акколь', 113);
INSERT INTO region_kn (id, key, name, region_id) VALUES (11, 'zhambylskaya-oblast', 'Жамбылская обл.', 227);
INSERT INTO region_kn (id, key, name, region_id) VALUES (54, 'kostanayskaya-oblast', 'Костанайская обл.', 247);
INSERT INTO region_kn (id, key, name, region_id) VALUES (12, 'zhanaozen', 'Жанаозен', 259);
INSERT INTO region_kn (id, key, name, region_id) VALUES (55, 'kostanay', 'Костанай', 250);
INSERT INTO region_kn (id, key, name, region_id) VALUES (14, 'zharkent', 'Жаркент', 160);
INSERT INTO region_kn (id, key, name, region_id) VALUES (13, 'zhanatas', 'Жанатас', 228);
INSERT INTO region_kn (id, key, name, region_id) VALUES (15, 'zhezkazgan', 'Жезказган', 238);
INSERT INTO region_kn (id, key, name, region_id) VALUES (17, 'sarkand', 'Сарканд', 193);
INSERT INTO region_kn (id, key, name, region_id) VALUES (18, 'satpaev', 'Сатпаев', 244);
INSERT INTO region_kn (id, key, name, region_id) VALUES (19, 'severo-kazahstanskaya-oblast', 'Северо-Казахстанская обл.', 264);
INSERT INTO region_kn (id, key, name, region_id) VALUES (20, 'semey', 'Семей', 222);
INSERT INTO region_kn (id, key, name, region_id) VALUES (56, 'kokshetau', 'Кокшетау', 119);
INSERT INTO region_kn (id, key, name, region_id) VALUES (57, 'kentau', 'Кентау', 273);
INSERT INTO region_kn (id, key, name, region_id) VALUES (58, 'kaskelen', 'Каскелен', 172);
INSERT INTO region_kn (id, key, name, region_id) VALUES (59, 'karatau', 'Каратау', 229);
INSERT INTO region_kn (id, key, name, region_id) VALUES (60, 'karazhal', 'Каражал', 240);
INSERT INTO region_kn (id, key, name, region_id) VALUES (61, 'karagandinskaya-oblast', 'Карагандинская обл.', 235);
INSERT INTO region_kn (id, key, name, region_id) VALUES (22, 'stepnogorsk', 'Степногорск', 121);
INSERT INTO region_kn (id, key, name, region_id) VALUES (23, 'zapadno-kazahstanskaya-oblast', 'Западно-Казахстанская обл.', 232);
INSERT INTO region_kn (id, key, name, region_id) VALUES (24, 'zyryanovsk', 'Зыряновск', 219);
INSERT INTO region_kn (id, key, name, region_id) VALUES (25, 'tayynsha', 'Тайынша', 269);
INSERT INTO region_kn (id, key, name, region_id) VALUES (26, 'talgar', 'Талгар', 194);
INSERT INTO region_kn (id, key, name, region_id) VALUES (27, 'taldykorgan', 'Талдыкорган', 195);
INSERT INTO region_kn (id, key, name, region_id) VALUES (28, 'taraz', 'Тараз', 230);
INSERT INTO region_kn (id, key, name, region_id) VALUES (29, 'tekeli', 'Текели', 197);
INSERT INTO region_kn (id, key, name, region_id) VALUES (30, 'temirtau', 'Темиртау', 245);
INSERT INTO region_kn (id, key, name, region_id) VALUES (31, 'turkestan', 'Туркестан', 276);
INSERT INTO region_kn (id, key, name, region_id) VALUES (32, 'usharal', 'Ушарал', 202);
INSERT INTO region_kn (id, key, name, region_id) VALUES (33, 'ushtobe', 'Уштобе', 1745);
INSERT INTO region_kn (id, key, name, region_id) VALUES (34, 'hromtau', 'Хромтау', 129);
INSERT INTO region_kn (id, key, name, region_id) VALUES (35, 'shahtinsk', 'Шахтинск', 246);
INSERT INTO region_kn (id, key, name, region_id) VALUES (36, 'shu', 'Шу', 231);
INSERT INTO region_kn (id, key, name, region_id) VALUES (37, 'shymkent', 'Шымкент', 278);
INSERT INTO region_kn (id, key, name, region_id) VALUES (38, 'shchuchinsk', 'Щучинск', 123);
INSERT INTO region_kn (id, key, name, region_id) VALUES (39, 'ekibastuz', 'Экибастуз', 263);
INSERT INTO region_kn (id, key, name, region_id) VALUES (47, 'mangistauskaya-oblast', 'Мангыстауская обл.', 257);
INSERT INTO region_kn (id, key, name, region_id) VALUES (50, 'kyzylordinskaya-oblast', 'Кызылординская обл.', 253);
INSERT INTO region_kn (id, key, name, region_id) VALUES (16, 'saran', 'Сарань', 243);
INSERT INTO region_kn (id, key, name, region_id) VALUES (21, 'sergeevka', 'Сергеевка', 1317);
INSERT INTO region_kn (id, key, name, region_id) VALUES (62, 'kapshagay', 'Капшагай', 2000);
INSERT INTO region_kn (id, key, name, region_id) VALUES (40, 'yuzhno-kazahstanskaya-oblast', 'Южно-Казахстанская обл.', 270);
INSERT INTO region_kn (id, key, name, region_id) VALUES (41, 'rudniy', 'Рудный', 252);
INSERT INTO region_kn (id, key, name, region_id) VALUES (42, 'ridder', 'Риддер', 221);
INSERT INTO region_kn (id, key, name, region_id) VALUES (43, 'priozersk', 'Приозерск', 242);
INSERT INTO region_kn (id, key, name, region_id) VALUES (44, 'petropavlovsk', 'Петропавловск', 267);
INSERT INTO region_kn (id, key, name, region_id) VALUES (45, 'pavlodarskaya-oblast', 'Павлодарская обл.', 260);
INSERT INTO region_kn (id, key, name, region_id) VALUES (46, 'pavlodar', 'Павлодар', 262);
INSERT INTO region_kn (id, key, name, region_id) VALUES (48, 'mamlyutka', 'Мамлютка', 266);
INSERT INTO region_kn (id, key, name, region_id) VALUES (49, 'lisakovsk', 'Лисаковск', 251);
INSERT INTO region_kn (id, key, name, region_id) VALUES (51, 'kyzylorda', 'Кызылорда', 256);
INSERT INTO region_kn (id, key, name, region_id) VALUES (52, 'kurchatov', 'Курчатов', 220);
INSERT INTO region_kn (id, key, name, region_id) VALUES (63, 'akmolinskaya-oblast', 'Акмолинская обл.', 112);
INSERT INTO region_kn (id, key, name, region_id) VALUES (64, 'aksay', 'Аксай', 1787);
INSERT INTO region_kn (id, key, name, region_id) VALUES (65, 'aksu', 'Аксу', 261);
INSERT INTO region_kn (id, key, name, region_id) VALUES (66, 'aktobe', 'Актобе', 125);
INSERT INTO region_kn (id, key, name, region_id) VALUES (67, 'aktyubinskaya-oblast', 'Актюбинская обл.', 124);
INSERT INTO region_kn (id, key, name, region_id) VALUES (68, 'aralsk', 'Аральск', 254);
INSERT INTO region_kn (id, key, name, region_id) VALUES (69, 'arkalyk', 'Аркалык', 248);
INSERT INTO region_kn (id, key, name, region_id) VALUES (71, 'atyrauskaya-oblast', 'Атырауская обл.', 213);
INSERT INTO region_kn (id, key, name, region_id) VALUES (72, 'ayagoz', 'Аягоз', 217);
INSERT INTO region_kn (id, key, name, region_id) VALUES (74, 'balhash', 'Балхаш', 237);
INSERT INTO region_kn (id, key, name, region_id) VALUES (75, 'borovoe', 'Боровое', 115);
INSERT INTO region_kn (id, key, name, region_id) VALUES (76, 'bulaevo', 'Булаево', 265);
INSERT INTO region_kn (id, key, name, region_id) VALUES (77, 'vostochno-kazahstanskaya-oblas', 'Восточно-Казахстанская обл.', 216);
INSERT INTO region_kn (id, key, name, region_id) VALUES (73, 'bayterek', 'Байтерек', 145);
INSERT INTO region_kn (id, key, name, region_id) VALUES (70, 'arys', 'Арысь', 271);
INSERT INTO region_kn (id, key, name, region_id) VALUES (94, '37557679', 'Федоровка', 33538);
INSERT INTO region_kn (id, key, name, region_id) VALUES (78, '42874623', 'Алатауский', 3);
INSERT INTO region_kn (id, key, name, region_id) VALUES (80, '27671457', 'Ауэзовский', 29);
INSERT INTO region_kn (id, key, name, region_id) VALUES (81, '8916474', 'Бостандыкский', 73);
INSERT INTO region_kn (id, key, name, region_id) VALUES (82, '28538280', 'Жетысуский', 83);
INSERT INTO region_kn (id, key, name, region_id) VALUES (85, '28451689', 'Медеуский', 89);
INSERT INTO region_kn (id, key, name, region_id) VALUES (87, '28968815', 'Турксибский', 99);
INSERT INTO region_kn (id, key, name, region_id) VALUES (79, '9256312', 'Алмалинский', 26);
INSERT INTO region_kn (id, key, name, region_id) VALUES (86, '24800606', 'Наурызбайский', 783);
INSERT INTO region_kn (id, key, name, region_id) VALUES (83, '34366614', 'Илийский', 34528);
INSERT INTO region_kn (id, key, name, region_id) VALUES (84, '31419755', 'Карасайский', 34529);
INSERT INTO region_kn (id, key, name, region_id) VALUES (95, '2927387', 'Алматинский', 106);
INSERT INTO region_kn (id, key, name, region_id) VALUES (88, '2067403', 'Майкудук', 34530);
INSERT INTO region_kn (id, key, name, region_id) VALUES (89, '21618525', 'Михайловка', 34531);
INSERT INTO region_kn (id, key, name, region_id) VALUES (90, '26466238', 'Пришахтинск', 34532);
INSERT INTO region_kn (id, key, name, region_id) VALUES (91, '36231046', 'р-н ЖБИ', 34533);
INSERT INTO region_kn (id, key, name, region_id) VALUES (92, '37238760', 'р-н Нового рынка', 33534);
INSERT INTO region_kn (id, key, name, region_id) VALUES (96, '6309988', 'Сарыаркинский', 107);
INSERT INTO region_kn (id, key, name, region_id) VALUES (97, '26636760', 'Есильский', 291);
INSERT INTO region_kn (id, key, name, region_id) VALUES (98, '34191971', 'Косши', 731);
INSERT INTO region_kn (id, key, name, region_id) VALUES (93, '6776875', 'Юго-Восток', 33536);


--
-- TOC entry 2375 (class 0 OID 87075)
-- Dependencies: 185
-- Data for Name: region_krisha; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23227, 'Жем', 2002, 2002, NULL, 48.4630013, NULL, NULL, 58.0429993, NULL, 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23169, 'Горняцкий', 1751, 1751, 'gornyaczkij', 52.9971008, '1229', '3', 63.1228981, '1230', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22974, 'Новоишимское', 977, 977, 'novoishimskoe', 53.4705009, '1415', '3', 66.6824036, '1416', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34472, 'Дзержинец', 34471, 1917, 'dzerzhinecz', 0, '1032', '4', 0, '1033', 'COUNTRY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34334, 'Дивон-ле-Бен', 34333, 1299, 'divon-le-ben', 46.854599, '2240', '2', 3.66494989, '2241', 'CITY', '5');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23192, 'Кенгир', 1809, 1809, 'kengir', 47.9737015, '1165', '3', 73.9023972, '1166', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22590, 'Казахстан', 1, 1, NULL, 48.5481987, '1', '1', 67.6274033, '1506', 'COUNTRY', '4');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22591, 'Алматы', 2, 2, 'almaty', 43.2859001, '2', '2', 76.9129028, '305', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22592, 'Алатауский р-н', 3, 3, 'almaty-alatauskij', 43.2812996, '3', '3', 76.8519974, '62', 'DISTRICT', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22593, 'мкр 6-й градокомплекс', 4, 4, 'almaty-alatauskij-mkr-6-gradokompleks', 43.2994003, '14', '4', 76.8432007, '15', 'MICRODISTRICT', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22594, 'мкр АДК', 5, 5, 'almaty-alatauskij-mkr-adk', 43.3158989, '16', '4', 76.8162003, '17', 'MICRODISTRICT', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22595, 'мкр Айгерим-1', 6, 6, 'almaty-alatauskij-mkr-ajgerim-1', 43.280899, '18', '4', 76.8462982, '19', 'MICRODISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22596, 'мкр Айгерим-2', 7, 7, 'almaty-alatauskij-mkr-ajgerim-2', 43.2700996, '20', '4', 76.8405991, '21', 'MICRODISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22597, 'мкр Акбулак', 8, 8, 'almaty-alatauskij-mkr-akbulak', 43.2569008, '22', '4', 76.8314972, '23', 'MICRODISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22598, 'мкр Алгабас', 9, 9, 'almaty-alatauskij-mkr-algabas', 43.2565994, '26', '4', 76.7958984, '27', 'MICRODISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22599, 'мкр Байбесик', 10, 10, 'almaty-alatauskij-mkr-bajbesik', 43.3097992, '28', '4', 76.8834991, '29', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22600, 'мкр Дархан', 11, 11, 'almaty-alatauskij-mkr-darhan', 43.3134003, '30', '4', 76.8759003, '31', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22601, 'мкр Заря Востока', 12, 12, 'almaty-alatauskij-mkr-zarja-vostoka', 43.2872009, '32', '4', 76.8831024, '33', 'MICRODISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22602, 'мкр Коккайнар', 13, 13, 'almaty-alatauskij-mkr-kokkajnar', 43.294899, '36', '4', 76.8406982, '37', 'MICRODISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22603, 'мкр Красный трудовик', 14, 14, 'almaty-alatauskij-mkr-krasnyj-trudovik', 43.3293991, '38', '4', 76.8481979, '39', 'MICRODISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22604, 'мкр Курылысшы', 15, 15, 'almaty-alatauskij-mkr-kurylysshy', 43.2677994, '40', '4', 76.8618011, '41', 'MICRODISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22605, 'мкр Ожет', 16, 16, 'almaty-alatauskij-mkr-ozhet', 43.3226013, '42', '4', 76.8880997, '43', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22606, 'мкр Туркестан', 17, 17, 'almaty-alatauskij-mkr-turkestan', 43.2762985, '44', '4', 76.8580017, '45', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22607, 'мкр Улжан-1', 18, 18, 'almaty-alatauskij-mkr-ulzhan-1', 43.3022003, '46', '4', 76.873497, '47', 'MICRODISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22608, 'мкр Улжан-2', 19, 19, 'almaty-alatauskij-mkr-ulzhan-2', 43.3042984, '48', '4', 76.8831024, '49', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22609, 'мкр Шанырак-1', 20, 20, 'almaty-alatauskij-mkr-shanyrak-1', 43.2952995, '50', '4', 76.8606033, '51', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22610, 'мкр Шанырак-2', 21, 21, 'almaty-alatauskij-mkr-shanyrak-2', 43.2910004, '52', '4', 76.8479004, '53', 'MICRODISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22611, 'мкр Шанырак-3', 22, 22, 'almaty-alatauskij-mkr-shanyrak-3', 43.2748985, '54', '4', 76.847702, '55', 'MICRODISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22612, 'мкр Шанырак-4', 23, 23, 'almaty-alatauskij-mkr-shanyrak-4', 43.2747993, '56', '4', 76.8303986, '57', 'MICRODISTRICT', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22613, 'мкр Шанырак-5', 24, 24, 'almaty-alatauskij-mkr-shanyrak-5', 43.3204002, '58', '4', 76.8415985, '59', 'MICRODISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22614, 'мкр Шанырак-6', 25, 25, 'almaty-alatauskij-mkr-shanyrak-6', 43.3286018, '60', '4', 76.8961029, '61', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22615, 'Алмалинский р-н', 26, 26, 'almaty-almalinskij', 43.2611008, '63', '3', 76.8945007, '68', 'DISTRICT', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22616, 'мкр Тастак-2', 27, 27, 'almaty-almalinskijj-tastak-2', 43.2523994, '64', '4', 76.8691025, '65', 'MICRODISTRICT', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22617, 'мкр Тастак-3', 28, 28, 'almaty-almalinskij-tastak-3', 43.2559013, '66', '4', 76.8833008, '67', 'MICRODISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22618, 'Ауэзовский р-н', 29, 29, 'almaty-aujezovskij', 43.2408981, '69', '3', 76.8389969, '162', 'DISTRICT', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22619, 'мкр №1', 30, 30, 'almaty-aujezovskij-mkr-1', 43.2308006, '70', '4', 76.8488998, '71', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22620, 'мкр №10', 31, 31, 'almaty-aujezovskij-mkr-10', 43.2170982, '72', '4', 76.8699036, '73', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22621, 'мкр №10 А', 32, 32, 'almaty-aujezovskij-mkr-10a', 43.2160988, '74', '4', 76.8593979, '75', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22622, 'мкр №11', 33, 33, 'almaty-aujezovskij-mkr-11', 43.2221985, '76', '4', 76.8735962, '77', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22623, 'мкр №12', 34, 34, 'almaty-aujezovskij-mkr-12', 43.2204018, '78', '4', 76.8643036, '79', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22624, 'мкр №2', 35, 35, 'almaty-aujezovskij-mkr-2', 43.2344017, '80', '4', 76.8638992, '81', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22625, 'мкр №3', 36, 36, 'almaty-aujezovskij-mkr-3', 43.2240982, '82', '4', 76.851799, '83', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22626, 'мкр №4', 37, 37, 'almaty-aujezovskij-mkr-4', 43.2274017, '84', '4', 76.8585968, '85', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22627, 'мкр №5', 38, 38, 'almaty-aujezovskij-mkr-5', 43.2313004, '86', '4', 76.8638, '87', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22628, 'мкр №6', 39, 39, 'almaty-aujezovskij-mkr-6', 43.2201996, '88', '4', 76.8576965, '89', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22629, 'мкр №7', 40, 40, 'almaty-aujezovskij-mkr-7', 43.2243996, '90', '4', 76.8622971, '91', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22630, 'мкр №8', 41, 41, 'almaty-aujezovskij-mkr-8', 43.2254982, '92', '4', 76.8691025, '93', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22631, 'мкр №9', 42, 42, 'almaty-aujezovskij-mkr-9', 43.2123985, '94', '4', 76.8638992, '95', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22632, 'мкр Аксай-1', 43, 43, 'almaty-aujezovskij-mkr-aksay-1', 43.2421989, '98', '4', 76.8330002, '99', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22633, 'мкр Аксай-1А', 44, 44, 'almaty-aujezovskij-mkr-aksay-1a', 43.2406998, '100', '4', 76.8256989, '101', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22634, 'мкр Аксай-2', 45, 45, 'almaty-aujezovskij-mkr-aksay-2', 43.2369995, '102', '4', 76.8354034, '103', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22635, 'мкр Аксай-2А', 46, 46, 'almaty-aujezovskij-mkr-aksay-2a', 43.2327003, '104', '4', 76.8376007, '105', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22785, 'Риддер', 221, 221, 'ridder', 50.1973991, '1015', '3', 83.1443024, '1016', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22636, 'мкр Аксай-3', 47, 47, 'almaty-aujezovskij-mkr-aksay-3', 43.2354012, '106', '4', 76.8302002, '107', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22637, 'мкр Аксай-3А', 48, 48, 'almaty-aujezovskij-mkr-aksay-3a', 43.2358017, '108', '4', 76.8265991, '109', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22638, 'мкр Аксай-3Б', 49, 49, 'almaty-aujezovskij-mkr-aksay-3b', 43.2346001, '110', '4', 76.8216019, '111', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22639, 'мкр Аксай-4', 50, 50, 'almaty-aujezovskij-mkr-aksay-4', 43.2277985, '112', '4', 76.8394012, '113', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22640, 'мкр Аксай-5', 51, 51, 'almaty-aujezovskij-mkr-aksay-5', 43.2272987, '114', '4', 76.8336029, '115', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22641, 'мкр Алтын Бесик', 52, 52, 'almaty-aujezovskij-mkr-altyn-besik', 43.2335014, '116', '4', 76.8162994, '117', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22642, 'мкр Астана', 53, 53, 'almaty-aujezovskij-mkr-astana', 43.2108994, '118', '4', 76.8531036, '119', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22643, 'мкр Баянаул', 54, 54, 'almaty-aujezovskij-mkr-bajanaul', 43.2389984, '120', '4', 76.8199005, '121', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22644, 'мкр Достык', 55, 55, 'almaty-aujezovskij-mkr-dostyk', 43.2215996, '122', '4', 76.8299026, '123', 'MICRODISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22645, 'мкр Дубок', 56, 56, 'almaty-aujezovskij-mkr-dubok', 43.1963005, '124', '4', 76.8921967, '125', 'MICRODISTRICT', '17');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22699, 'Ерейментау', 117, 117, 'erejmentau', 51.6248016, '351', '3', 73.1051025, '352', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22646, 'мкр Дубок-2', 57, 57, 'almaty-aujezovskij-mkr-dubok-2', 43.1964989, '126', '4', 76.908699, '127', 'MICRODISTRICT', '17');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22647, 'мкр Таугуль-2', 58, 58, 'almaty-aujezovskij-mkr-taugul-2', 43.2036018, '156', '4', 76.8451996, '157', 'MICRODISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22648, 'мкр Жетысу-1', 59, 59, 'almaty-aujezovskij-mkr-zhetysu-1', 43.2218018, '128', '4', 76.8406982, '129', 'MICRODISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22649, 'мкр Жетысу-2', 60, 60, 'almaty-aujezovskij-mkr-zhetysu-2', 43.2219009, '130', '4', 76.8407974, '131', 'MICRODISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22650, 'мкр Жетысу-3', 61, 61, 'almaty-aujezovskij-mkr-zhetysu-3', 43.2212982, '132', '4', 76.8423996, '133', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22651, 'мкр Жетысу-4', 62, 62, 'almaty-aujezovskij-mkr-zhetysu-4', 43.2220993, '134', '4', 76.8367996, '135', 'MICRODISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22652, 'мкр Мамыр', 65, 65, 'almaty-aujezovskij-mkr-mamyr', 43.2126007, '136', '4', 76.8458023, '137', 'MICRODISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22653, 'мкр Мамыр-7', 66, 66, 'almaty-aujezovskij-mkr-mamyr-7', 43.2126007, '146', '4', 76.8458023, '147', 'MICRODISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22654, 'мкр Сайран', 67, 67, 'almaty-aujezovskij-mkr-sayran', 43.2389984, '148', '4', 76.8797989, '149', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22655, 'мкр Таугуль', 69, 69, 'almaty-aujezovskij-mkr-taugul', 43.2131996, '152', '4', 76.8798981, '153', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22656, 'мкр Таугуль-1', 70, 70, 'almaty-aujezovskij-mkr-taugul-1', 43.2005005, '154', '4', 76.8551025, '155', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22657, 'мкр Таугуль-3', 71, 71, 'almaty-aujezovskij-mkr-taugul-3', 43.2002983, '158', '4', 76.8563995, '159', 'MICRODISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22658, 'мкр Школьный', 72, 72, 'almaty-aujezovskij-mkr-shkolnyj', 43.2061005, '160', '4', 76.8404999, '161', 'MICRODISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22659, 'Бостандыкский р-н', 73, 73, 'almaty-bostandykskij', 43.2134018, '163', '3', 76.8880005, '200', 'DISTRICT', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22660, 'мкр Алмагуль', 74, 74, 'almaty-bostandykskij-almagul', 43.2043991, '176', '4', 76.9012985, '177', 'MICRODISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22661, 'мкр Баганашыл', 75, 75, 'almaty-bostandykskij-baganashyl', 43.1986008, '178', '4', 76.9134979, '179', 'MICRODISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22662, 'мкр Казахфильм', 76, 76, 'almaty-bostandykskij-kazahfilm', 43.1955986, '180', '4', 76.9033966, '181', 'MICRODISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22663, 'мкр Коктем-1', 77, 77, 'almaty-bostandykskij-koktem-1', 43.2284012, '182', '4', 76.927597, '183', 'MICRODISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22664, 'мкр Коктем-2', 78, 78, 'almaty-bostandykskij-koktem-2', 43.2289009, '184', '4', 76.9160995, '185', 'MICRODISTRICT', '17');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22665, 'мкр Коктем-3', 79, 79, 'almaty-bostandykskij-koktem-3', 43.2349014, '186', '4', 76.9135971, '187', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22666, 'мкр Орбита-1', 80, 80, 'almaty-bostandykskij-orbita-1', 43.2011986, '192', '4', 76.8830032, '193', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22667, 'мкр Орбита-2', 81, 81, 'almaty-bostandykskij-orbita-2', 43.1972008, '194', '4', 76.8845978, '195', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22668, 'мкр Орбита-3', 82, 82, 'almaty-bostandykskij-orbita-3', 43.2000999, '196', '4', 76.8755035, '197', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22669, 'Жетысуский р-н', 83, 83, 'almaty-zhetysuskij', 43.3011017, '201', '3', 76.8983002, '218', 'DISTRICT', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22670, 'мкр Айнабулак-1', 84, 84, 'almaty-zhetysuskij-ajnabulak-1', 43.3209991, '204', '4', 76.9097977, '205', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22671, 'мкр Айнабулак-2', 85, 85, 'almaty-zhetysuskij-ajnabulak-2', 43.3165016, '206', '4', 76.9140015, '207', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22672, 'мкр Айнабулак-3', 86, 86, 'almaty-zhetysuskij-ajnabulak-3', 43.3235016, '208', '4', 76.9200974, '209', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22673, 'мкр Кулагер', 88, 88, 'almaty-zhetysuskij-kulager', 43.3041992, '214', '4', 76.9235992, '215', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22674, 'Медеуский р-н', 89, 89, 'almaty-medeuskij', 43.2282982, '219', '3', 76.9586029, '256', 'DISTRICT', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22675, 'мкр Атырау', 90, 90, 'almaty-medeuskij-atyrau', 43.2938995, '232', '4', 76.9860001, '233', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22676, 'мкр Бутаковка', 91, 91, 'almaty-medeuskij-butakovka', 43.1907997, '234', '4', 77.0289993, '235', 'MICRODISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22677, 'мкр Горный Гигант', 92, 92, 'almaty-medeuskij-gornyj-gigant', 43.2165985, '236', '4', 76.9496994, '237', 'MICRODISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22678, 'мкр Думан-1', 93, 93, 'almaty-medeuskij-duman-1', 43.2793007, '238', '4', 77.0027008, '239', 'MICRODISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22679, 'мкр Коктобе', 94, 94, 'almaty-medeuskij-koktobe', 43.2182999, '244', '4', 76.972702, '245', 'MICRODISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22680, 'мкр Самал-1', 95, 95, 'almaty-medeuskij-samal-1', 43.2368011, '248', '4', 76.9536972, '249', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22681, 'мкр Самал-2', 96, 96, 'almaty-medeuskij-samal-2', 43.2313995, '250', '4', 76.9546967, '251', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22682, 'мкр Самал-3', 97, 97, 'almaty-medeuskij-samal-3', 43.2256012, '252', '4', 76.956398, '253', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22683, 'мкр Тау Самал', 98, 98, 'almaty-medeuskij-tau-samal', 43.1991997, '254', '4', 76.9763031, '255', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22684, 'Турксибский р-н', 99, 99, 'almaty-turksibskij', 43.3459015, '285', '3', 76.9776001, '304', 'DISTRICT', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22685, 'мкр Алтай-1', 100, 100, 'almaty-turksibskij-altaj-1', 43.3446007, '288', '4', 76.9872971, '289', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22686, 'мкр Алтай-2', 101, 101, 'almaty-turksibskij-altaj-2', 43.3506012, '290', '4', 76.9898987, '291', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22687, 'мкр Жулдыз-1', 102, 102, 'almaty-turksibskij-zhuldyz-1', 43.3630981, '292', '4', 76.9950027, '293', 'MICRODISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22688, 'мкр Жулдыз-2', 103, 103, 'almaty-turksibskij-zhuldyz-2', 43.3581009, '294', '4', 76.9929962, '295', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22689, 'мкр Маяк', 104, 104, 'almaty-turksibskij-majak', 43.3334007, '302', '4', 76.9923019, '303', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22690, 'Астана', 105, 105, 'astana', 51.1582985, '306', '2', 71.4313965, '317', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22691, 'Алматинский р-н', 106, 106, 'astana-almatinskij', 51.1328011, '311', '3', 71.5100021, '312', 'DISTRICT', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22692, 'Сарыаркинский р-н', 107, 107, 'astana-saryarkinskij', 51.2062988, '313', '3', 71.3869019, '316', 'DISTRICT', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22693, 'Байконыр', 111, 111, 'bajkonyr', 45.6302986, '318', '2', 63.3022003, '319', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22694, 'Акмолинская обл.', 112, 112, 'akmolinskaja-oblast', 52.1357994, '320', '2', 70.0003967, '465', 'REGION', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22695, 'Акколь', 113, 113, 'akkol', 51.9948997, '323', '3', 70.9325027, '324', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22696, 'Атбасар', 114, 114, 'atbasar', 51.8097, '331', '3', 68.3495026, '332', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22697, 'Боровое', 115, 115, 'borovoe', 53.0821991, '337', '3', 70.310997, '338', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22698, 'Державинск', 116, 116, 'derzhavinsk', 51.1049004, '345', '3', 66.3147964, '346', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22700, 'Есиль', 118, 118, 'esil', 51.9546013, '353', '3', 66.4057999, '354', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22701, 'Кокшетау', 119, 119, 'kokshetau', 53.2863007, '387', '3', 69.3945999, '388', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22702, 'Макинск', 120, 120, 'makinsk', 52.6357994, '411', '3', 70.4271011, '412', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22703, 'Степногорск', 121, 121, 'stepnogorsk', 52.3466988, '435', '3', 71.8797989, '436', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22704, 'Степняк', 122, 122, 'stepnjak', 52.8372002, '437', '3', 70.7848969, '438', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22705, 'Щучинск', 123, 123, 'shhuchinsk', 52.9322014, '463', '3', 70.1837006, '464', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22706, 'Актюбинская обл.', 124, 124, 'aktjubinskaja-oblast', 49.1890984, '466', '2', 57.289299, '529', 'REGION', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22707, 'Актобе', 125, 125, 'aktobe', 50.2827988, '471', '3', 57.1907997, '478', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22708, 'Алга', 126, 126, 'alga', 49.8951988, '481', '3', 57.3274002, '482', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22709, 'Кандыагаш', 127, 127, 'kandyagash', 49.4625015, '489', '3', 57.4185982, '490', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22710, 'Темир', 128, 128, 'temir', 49.1453018, '517', '3', 57.1268997, '518', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22711, 'Хромтау', 129, 129, 'hromtau', 50.2542992, '519', '3', 58.4459991, '520', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22712, 'Шалкар', 130, 130, 'shalkar', 47.8339005, '521', '3', 59.6162987, '522', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22713, 'Эмба', 131, 131, 'jemba', 48.8210983, '525', '3', 58.1472015, '526', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22714, 'Алматинская обл.', 132, 132, 'almatinskaja-oblast', 45.2150993, '530', '2', 77.3393021, '909', 'REGION', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22715, 'Абай', 133, 133, 'almatinskaja-oblast-abaj', 43.2112007, '541', '3', 76.7628021, '542', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22716, 'Азат', 134, 134, 'azat', 43.3465004, '551', '3', 77.2646027, '552', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22717, 'Аксенгир', 136, 136, 'aksengir', 43.5008011, '561', '3', 76.2742996, '562', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22718, 'Алмалы', 139, 139, 'almaly', 43.4003983, '571', '3', 77.5250015, '572', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22719, 'Алмалыбак (КИЗ)', 140, 140, 'almalybak-kiz', 43.2192993, '575', '3', 76.6821976, '576', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22720, 'Ащибулак', 142, 142, 'ashhibulak', 43.4123001, '585', '3', 76.9132004, '588', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22721, 'Баганашыл', 143, 143, 'baganashyl', 43.1917992, '591', '3', 76.910202, '592', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22722, 'Байсерке', 144, 144, 'bajserke', 43.4911003, '597', '3', 77.0512009, '598', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22723, 'Байтерек (Новоалексеевка)', 145, 145, 'bajterek-novoalekseevka', 43.4033012, '599', '3', 77.2247009, '600', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22724, 'Баканас', 146, 146, 'bakanas', 44.8111992, '601', '3', 76.2705002, '602', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22725, 'Балтабай', 147, 147, 'baltabaj', 43.5047989, '607', '3', 77.5422974, '608', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22726, 'Бельбулак (Мичурино)', 148, 148, 'belbulak-michurino', 43.3158989, '615', '3', 77.1007004, '616', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22727, 'Бирлик', 149, 149, 'birlik', 43.4972992, '619', '3', 77.5201035, '620', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22728, 'Бесагаш (Дзержинское)', 150, 150, 'besagash-dzerzhinskoe', 43.3013992, '621', '3', 77.0406036, '622', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22729, 'Бескайнар (Горный Садовод)', 151, 151, 'beskajnar-gornyj-sadovod', 43.2200012, '623', '3', 77.0995026, '624', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22730, 'Верхняя Каменка', 152, 152, 'verhnjaja-kamenka', 43.1904984, '625', '3', 76.8327026, '626', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22731, 'Боралдай (Бурундай)', 153, 153, 'boroldaj-burundaj', 43.3577003, '629', '3', 76.8574982, '630', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22732, 'Гулдала', 154, 154, 'guldala', 43.3493996, '633', '3', 77.0537033, '634', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22733, 'Есик', 156, 156, 'esik', 43.3460999, '539', '3', 77.4708023, '540', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22734, 'Жалкамыс', 157, 157, 'zhalkamys', 43.535099, '655', '3', 77.1511002, '656', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22735, 'Жанатурмыс', 158, 158, 'zhanaturmys', 43.1817017, '669', '3', 76.7822037, '670', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22736, 'Жандосов', 159, 159, 'zhandosov', 43.1660004, '675', '3', 76.5662003, '676', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22737, 'Жаркент', 160, 160, 'zharkent', 44.1661987, '679', '3', 80.0037994, '680', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22738, 'Жармухамбет', 161, 161, 'zharmuhambet', 43.3759995, '681', '3', 76.8034973, '682', 'CITY', '17');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22739, 'Жетыген', 162, 162, 'zhetygen', 43.6822014, '683', '3', 77.1227036, '684', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22740, 'Заречное', 163, 163, 'zarechnoe', 50.1579018, '689', '3', 71.4459, '690', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22741, 'Иргели', 164, 164, 'irgeli', 43.2336998, '695', '3', 76.7701035, '696', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22742, 'ИЯФ', 165, 165, 'ijaf', 43.3507996, '697', '3', 77.1404037, '698', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22743, 'КазЦИК', 166, 166, 'kazcik', 43.3815994, '699', '3', 76.8503036, '700', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22744, 'Капчагай', 168, 168, 'kapchagaj', 43.8634987, '537', '3', 77.0653, '538', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22745, 'Карабулак (п.Ключи)', 169, 169, 'karabulak', 43.3977013, '709', '3', 77.151001, '710', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22746, 'Каргалы (п. Фабричный)', 171, 171, 'kargaly-fabrichnyj', 43.1654015, '725', '3', 76.4097977, '726', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22747, 'Каскелен', 172, 172, 'kaskelen', 43.2028008, '535', '3', 76.6276016, '536', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22748, 'Кемертоган', 173, 173, 'kemertogan', 43.2364998, '729', '3', 76.6648026, '730', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22749, 'Кок-Лай-Сай', 174, 174, 'kok-laj-saj', 43.2501984, '741', '3', 76.7583008, '742', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22750, 'Кокозек', 175, 175, 'kokozek', 47.1436005, '745', '3', 81.892601, '746', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22751, 'Коянкоз', 178, 178, 'kojankoz', 43.3941994, '765', '3', 76.9371033, '766', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22752, 'Кошмамбет', 179, 179, 'koshmambet', 43.3404999, '769', '3', 76.6617966, '770', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22753, 'Кыргауылды', 180, 180, 'kyrgauyldy', 43.1721992, '789', '3', 76.7664032, '790', 'CITY', '17');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22754, 'М. Туймебаев', 181, 181, 'm-tujmebaev', 43.3419991, '793', '3', 76.9385986, '794', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22755, 'Мерей (Селекция)', 183, 183, 'merej-selekcija', 43.3320007, '797', '3', 76.7016983, '798', 'CITY', '17');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22756, 'Нура', 184, 184, 'nura', 43.6875992, '811', '3', 77.1930008, '812', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22757, 'Отеген батыр', 185, 185, 'otegen-batyr', 43.421299, '815', '3', 77.0328979, '816', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22758, 'Панфилова (Табаксовхоз)', 186, 186, 'panfilova-tabaksovhoz', 43.3891983, '819', '3', 77.1147995, '820', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22759, 'Райымбек', 188, 188, 'rajymbek', 43.2033997, '821', '3', 76.7436981, '822', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22760, 'Рахат (Покровка)', 189, 189, 'rahat-pokrovka', 43.4081001, '823', '3', 77.0089035, '824', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22761, 'Самсы', 192, 192, 'samsy', 43.4720993, '829', '3', 76.117897, '830', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22762, 'Сарканд', 193, 193, 'sarkand', 45.4132004, '835', '3', 79.917099, '836', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22763, 'Талгар', 194, 194, 'talgar', 43.3092003, '841', '3', 77.2417984, '842', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22764, 'Талдыкорган', 195, 195, 'taldykorgan', 45.0140991, '533', '3', 78.3640976, '534', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22765, 'Текели', 197, 197, 'tekeli', 44.8633995, '847', '3', 78.7650986, '848', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22766, 'Туздыбастау (Калинино)', 199, 199, 'tuzdybastau-kalinino', 43.3055992, '863', '3', 77.0653992, '864', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22767, 'Турар', 200, 200, 'turar', 43.3166008, '867', '3', 76.5856018, '868', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22768, 'Турген', 201, 201, 'turgen', 43.3941994, '869', '3', 77.5887985, '870', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22769, 'Ушарал', 202, 202, 'usharal', 43.4306984, '883', '3', 70.7739029, '884', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22770, 'Уштобе', 203, 203, 'ushtobe', 45.2514992, '887', '3', 77.9769974, '888', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22771, 'Чилик', 204, 204, 'chilik', 43.5960999, '893', '3', 78.2498016, '894', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22772, 'Чунджа', 205, 205, 'chundzha', 43.5383987, '895', '3', 79.4630966, '896', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22773, 'Каратал', 206, 206, 'karatalskij-rayon', 45.8083992, '723', '3', 77.1756973, '724', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22774, 'Ушконыр', 207, 207, 'shamalgan', 43.1893005, '885', '3', 76.5354996, '886', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22775, 'Станция Шамалган', 211, 211, 'stancija-shamalgan', 43.3721008, '839', '3', 76.6272964, '840', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22776, 'Жаналык', 212, 212, 'zhanalyk', 44.4005013, '667', '3', 78.5101013, '668', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22777, 'Атырауская обл.', 213, 213, 'atyrauskaja-oblast', 47.3865013, '910', '2', 52.5798988, '949', 'REGION', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22778, 'Атырау', 214, 214, 'atyrau', 47.1013985, '911', '3', 51.9096985, '912', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22779, 'Кульсары', 215, 215, 'kulsary', 46.9602013, '937', '3', 53.9980011, '938', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22780, 'Восточно-Казахстанская обл.', 216, 216, 'vostochno-kazahstanskaja-oblast', 48.8246994, '950', '2', 81.2285004, '1041', 'REGION', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22781, 'Аягоз', 217, 217, 'ajagoz', 47.5560989, '961', '3', 80.4921036, '962', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22782, 'Зайсан', 218, 218, 'zajsan', 47.5747986, '981', '3', 84.2425003, '982', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22783, 'Зыряновск', 219, 219, 'zyrjanovsk', 49.7401009, '983', '3', 83.3087997, '984', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22784, 'Курчатов', 220, 220, 'kurchatov', 50.6253014, '995', '3', 79.1159973, '996', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22786, 'Семей', 222, 222, 'semej', 50.4328995, '1017', '3', 80.2565994, '1020', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22787, 'Серебрянск', 223, 223, 'serebrjansk', 49.7840004, '1021', '3', 82.9235992, '1022', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22788, 'Усть-Каменогорск', 224, 224, 'ust-kamenogorsk', 49.9524002, '951', '3', 82.6016998, '954', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22789, 'Шар', 225, 225, 'shar', 49.6680984, '1035', '3', 81.7302017, '1036', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22790, 'Шемонаиха', 226, 226, 'shemonaiha', 50.4342995, '1037', '3', 81.9457016, '1038', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22791, 'Жамбылская обл.', 227, 227, 'zhambylskaja-oblast', 44.2557983, '1042', '2', 72.0988007, '1089', 'REGION', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22792, 'Жанатас', 228, 228, 'zhanatas', 43.5634003, '1057', '3', 69.7357025, '1058', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22793, 'Каратау', 229, 229, 'karatau', 43.1819, '1061', '3', 70.4598999, '1062', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22794, 'Тараз', 230, 230, 'taraz', 42.8916016, '1075', '3', 71.3921967, '1076', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22795, 'Шу', 231, 231, 'shu', 43.6054001, '1083', '3', 73.7612991, '1084', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22796, 'Западно-Казахстанская обл.', 232, 232, 'zapadno-kazahstanskaja-oblast', 49.9889984, '1090', '2', 50.5107002, '1133', 'REGION', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22797, 'Аксай', 233, 233, 'aksaj', 51.1674995, '1091', '3', 53.0149002, '1092', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22798, 'Уральск', 234, 234, 'uralsk', 51.2302017, '1127', '3', 51.4310989, '1128', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22799, 'Карагандинская обл.', 235, 235, 'karagandinskaja-oblast', 49.5733986, '1134', '2', 73.7798004, '1211', 'REGION', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22800, 'Абай', 236, 236, 'karagandinskaja-oblast-abaj', 49.6323013, '1143', '3', 72.8673019, '1144', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22801, 'Балхаш', 237, 237, 'balhash', 46.8440018, '1151', '3', 74.9748993, '1152', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22802, 'Жезказган', 238, 238, 'zhezkazgan', 47.7890015, '1159', '3', 67.714798, '1160', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22803, 'Караганда', 239, 239, 'karaganda', 49.7845001, '1137', '3', 73.0979996, '1142', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22804, 'Каражал', 240, 240, 'karazhal', 48.0108986, '1161', '3', 70.7949982, '1162', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22805, 'Каркаралинск', 241, 241, 'karkaralinsk', 49.4105988, '1163', '3', 75.4725037, '1164', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22806, 'Приозёрск', 242, 242, 'priozjorsk', 46.0332985, '1181', '3', 73.6929016, '1182', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22807, 'Сарань', 243, 243, 'saran', 49.7981987, '1185', '3', 72.8451004, '1186', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22808, 'Сатпаев', 244, 244, 'satpaev', 47.9023018, '1189', '3', 67.5317993, '1190', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22809, 'Темиртау', 245, 245, 'temirtau', 50.0634995, '1193', '3', 72.9589005, '1194', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22810, 'Шахтинск', 246, 246, 'shahtinsk', 49.7088013, '1205', '3', 72.5914001, '1206', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22811, 'Костанайская обл.', 247, 247, 'kostanajskaja-oblast', 51.4696999, '1212', '2', 63.9799004, '1289', 'REGION', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22812, 'Аркалык', 248, 248, 'arkalyk', 50.2560005, '1219', '3', 66.9052963, '1220', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22813, 'Житикара', 249, 249, 'zhitikara', 52.1633987, '1237', '3', 61.2487984, '1238', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22814, 'Костанай', 250, 250, 'kostanaj', 53.2072983, '1249', '3', 63.6747017, '1250', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22815, 'Лисаковск', 251, 251, 'lisakovsk', 52.5661011, '1257', '3', 62.5643997, '1258', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22816, 'Рудный', 252, 252, 'rudnyj', 52.9756012, '1269', '3', 63.1236992, '1270', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22817, 'Кызылординская обл.', 253, 253, 'kyzylordinskaja-oblast', 45.3424988, '1290', '2', 63.2988014, '1309', 'REGION', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22818, 'Аральск', 254, 254, 'aralsk', 46.7994003, '1291', '3', 61.6749001, '1292', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22819, 'Казалинск', 255, 255, 'kazalinsk', 45.7612991, '1299', '3', 62.1044006, '1300', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22820, 'Кызылорда', 256, 256, 'kyzylorda', 44.8507996, '1303', '3', 65.5033035, '1304', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22821, 'Мангистауская обл.', 257, 257, 'mangistauskaja-oblast', 44.264801, '1310', '2', 53.7626991, '1351', 'REGION', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22822, 'Актау', 258, 258, 'aktau', 43.6464996, '1311', '3', 51.1721992, '1312', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22823, 'Жанаозен', 259, 259, 'zhanaozen', 43.3342018, '1329', '3', 52.8712997, '1330', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22824, 'Павлодарская обл.', 260, 260, 'pavlodarskaja-oblast', 52.0808983, '1352', '2', 76.7461014, '1397', 'REGION', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22825, 'Аксу', 261, 261, 'aksu', 52.0382004, '1355', '3', 76.9292984, '1356', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22826, 'Павлодар', 262, 262, 'pavlodar', 52.2942009, '1353', '3', 76.9717026, '1354', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22827, 'Экибастуз', 263, 263, 'ekibastuz', 51.7294998, '1395', '3', 75.3299026, '1396', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22828, 'Северо-Казахстанская обл.', 264, 264, 'severo-kazahstanskaja-oblast', 54.4275017, '1398', '2', 68.6491013, '1433', 'REGION', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22829, 'Булаево', 265, 265, 'bulaevo', 54.898201, '1403', '3', 70.4496994, '1404', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22830, 'Мамлютка', 266, 266, 'mamljutka', 54.9366989, '1413', '3', 68.5395966, '1414', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22831, 'Петропавловск', 267, 267, 'petropavlovsk', 54.8737984, '1419', '3', 69.1740036, '1420', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22832, 'Саумалколь', 268, 268, 'saumalkol', 53.8865013, '1423', '3', 67.4173965, '1424', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22833, 'Тайынша', 269, 269, 'tajynsha', 53.8471985, '1427', '3', 69.7683029, '1428', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22834, 'Южно-Казахстанская обл.', 270, 270, 'juzhno-kazahstanskaja-oblast', 43.4954987, '1434', '2', 68.4293976, '1505', 'REGION', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22835, 'Арысь', 271, 271, 'arys', 42.4173012, '1451', '3', 68.8080978, '1452', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22836, 'Жетысай', 272, 272, 'zhetysaj', 40.7756996, '1457', '3', 68.3323975, '1458', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22837, 'Кентау', 273, 273, 'kentau', 43.5115013, '1467', '3', 68.5105972, '1468', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22838, 'Ленгер', 274, 274, 'lenger', 42.1813011, '1475', '3', 69.8841019, '1476', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22839, 'Сарыагаш', 275, 275, 'saryagash', 41.8339996, '1483', '3', 69.0086975, '1484', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22840, 'Туркестан', 276, 276, 'turkestan', 43.2971992, '1491', '3', 68.2587967, '1492', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22841, 'Шардара', 277, 277, 'shardara', 41.2653008, '1501', '3', 67.9810028, '1502', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22842, 'Шымкент', 278, 278, 'shymkent', 42.3174019, '1435', '3', 69.620697, '1444', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22843, 'Саймасай', 290, 290, 'sajmasaj', 43.4453011, '827', '3', 77.3320007, '828', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22844, 'Есильский р-н', 291, 291, 'astana-esilskij', 51.1226997, '307', '3', 71.3665009, '310', 'DISTRICT', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22845, 'Батан', 302, 302, 'batan', 43.259201, '613', '3', 77.7289963, '614', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22846, 'Кызыл Кайрат', 304, 304, 'kyzyl-kajrat', 43.3017006, '783', '3', 77.108902, '784', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22847, 'Жапек батыр', 305, 305, 'zhapek-batyr', 43.4135017, '677', '3', 76.8944016, '678', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22848, 'Ынтымак', 312, 312, 'yntymak', 43.4151001, '897', '3', 76.9665985, '898', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22849, 'Междуреченск', 313, 313, 'mezhdurechensk', 43.3429985, '799', '3', 76.6697006, '800', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22850, 'Жанашар', 314, 314, 'zhanashar', 43.4407005, '673', '3', 77.259903, '674', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22851, 'Береке', 316, 316, 'bereke', 43.3023987, '617', '3', 77.0858994, '618', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22852, 'Карагайлы (Чапаево)', 318, 318, 'chapaevo', 43.1299019, '713', '3', 76.8447037, '714', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22853, 'Акши', 321, 321, 'akshi', 43.4201012, '563', '3', 76.9456024, '564', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22854, 'Косозен', 324, 324, 'kosozen', 43.5584984, '759', '3', 76.9169998, '760', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22855, 'Узынагаш', 325, 325, 'uzynagash', 43.2257004, '871', '3', 76.3162003, '872', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22856, 'Музей Жамбыла', 326, 326, 'muzej-zhambyla', 43.1977005, '801', '3', 76.6480026, '802', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22857, 'Улан', 327, 327, 'ulan', 43.3120995, '877', '3', 76.6691971, '878', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22858, 'Аршалы', 328, 328, 'arshaly', 50.8339005, '327', '3', 72.1821976, '328', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22859, 'Астраханка', 329, 329, 'astrahanka', 51.4888992, '329', '3', 69.7970963, '330', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22860, 'Егиндыколь', 330, 330, 'egindykol', 50.0393982, '349', '3', 54.0359001, '350', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22861, 'Жаксы', 331, 331, 'zhaksy', 51.9085007, '355', '3', 67.3162003, '356', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22862, 'Зеренда', 332, 332, 'zerenda', 52.9081993, '367', '3', 69.1549988, '368', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22863, 'Коргалжын', 333, 333, 'korgalzhyn', 50.5882988, '391', '3', 70.0241013, '392', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22864, 'Балкашино', 334, 334, 'balkashino', 52.5227013, '335', '3', 68.7555008, '336', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22865, 'Акмол', 335, 335, 'akmol', 51.0643005, '325', '3', 70.975502, '326', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22866, 'Шортанды', 336, 336, 'shortandy', 51.698101, '459', '3', 70.9974976, '460', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22867, 'Научный', 337, 337, 'nauchnyj', 51.6769981, '417', '3', 71.0162964, '418', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22868, 'Жолымбет', 338, 338, 'zholymbet', 51.7419014, '363', '3', 71.7161026, '364', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22869, 'Кабанбай Батыра', 341, 341, 'kabanbaj-batyra', 50.854599, '373', '3', 71.3584976, '374', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22870, 'Долан', 346, 346, 'dolan', 43.2529984, '651', '3', 76.6523972, '652', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22871, 'Арна', 348, 348, 'arna', 43.7240982, '581', '3', 77.1259995, '582', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22872, 'Унгуртас', 350, 350, 'ungurtas', 43.3130989, '875', '3', 76.0926971, '876', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22873, 'Кызыл ту-4', 353, 353, 'kyzyl-tu-4', 43.4034996, '781', '3', 77.0631027, '782', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22874, 'Касымбек', 355, 355, 'kasymbek', 43.2487984, '727', '3', 76.4371033, '728', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22875, 'Караой', 357, 357, 'karaoj', 44.7247009, '721', '3', 76.2985001, '722', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22876, 'Мынбаев', 358, 358, 'mynbaev', 43.2961998, '803', '3', 76.6564026, '804', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22877, 'Алма-Арасан', 360, 360, 'alma-arasan', 43.0878983, '569', '3', 76.9096985, '570', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22878, 'Жанадауыр', 361, 361, 'zhanadauyr', 43.4385986, '671', '3', 76.9589005, '672', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22879, 'Айтей', 362, 362, 'ajtej', 43.2331009, '549', '3', 76.6010971, '550', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22880, 'Космос', 363, 363, 'kosmos', 43.4996986, '763', '3', 77.2603989, '764', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22881, 'Жамбыл', 365, 365, 'zhambyl', 43.2724991, '665', '3', 76.6738968, '666', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22882, 'Ават', 366, 366, 'avat', 43.4001007, '543', '3', 77.2707977, '544', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22883, 'Максимовка', 368, 368, 'maksimovka', 51.2937012, '413', '3', 71.0203018, '414', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22884, 'Талапкер', 369, 369, 'talapker', 51.2411995, '443', '3', 71.1656036, '444', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22885, 'Кажымукан', 370, 370, 'kazhymukan', 51.2661018, '375', '3', 71.0774994, '376', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22886, 'Актас', 371, 371, 'aktas', 49.7831001, '1147', '3', 72.9576035, '1148', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22887, 'Улькен', 372, 372, 'ulken', 43.3432007, '873', '3', 76.4024963, '874', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22888, 'Чиганак', 373, 373, 'chiganak', 42.8688011, '1087', '3', 73.1085968, '1088', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22889, 'Ащысай', 381, 381, 'ashhysaj', 43.5531006, '589', '3', 78.0131989, '590', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22890, 'Темирлановка', 391, 391, 'temirlanovka', 42.5984001, '1489', '3', 69.2563019, '1490', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22891, 'Шахан', 392, 392, 'shahan', 49.8256989, '1203', '3', 72.6298981, '1204', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22892, 'Топар', 401, 401, 'topar', 49.5009003, '1195', '3', 72.8071976, '1196', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22893, 'Еламан', 403, 403, 'elaman', 43.3506012, '645', '3', 77.0715027, '646', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22894, 'Енбекши', 409, 409, 'enbekshi', 43.2372017, '649', '3', 76.5139999, '650', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22895, 'Комсомол', 410, 410, 'komsomol', 43.4300995, '761', '3', 76.8542023, '762', 'CITY', '17');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22896, 'Жана-талап', 411, 411, 'jana-talap', 43.4939995, '663', '3', 77.0154037, '664', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22897, 'Бекболат', 418, 418, 'bekbolat', 44.9640999, '611', '3', 77.9311981, '612', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22898, 'Шубар', 419, 419, 'shubar', 44.2503014, '905', '3', 77.7266006, '906', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22899, 'Талдыбулак', 420, 420, 'taldybulak', 43.3072014, '843', '3', 77.0166016, '844', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22900, 'Маловодное', 421, 421, 'malovodnoe', 43.512001, '795', '3', 77.6886978, '796', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22901, 'Коксай (Путь Ильича)', 426, 426, 'koksaj', 43.2574005, '747', '3', 76.7697983, '748', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22902, 'Базаркельды', 435, 435, 'bazarkel''dy', 43.3795013, '595', '3', 77.2218018, '596', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22903, 'Карасу-2', 436, 436, 'karasu-2', 43.3437004, '719', '3', 76.9128036, '720', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22904, 'Кайназар', 437, 437, 'kaynazar', 43.3630981, '703', '3', 77.3071976, '704', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22905, 'Софиевка', 438, 438, 'sofievka', 51.4031982, '433', '3', 71.7073975, '434', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22906, 'Сарыозек', 439, 439, 'saryozek', 44.3647003, '837', '3', 77.9692993, '838', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22907, 'Шелекемир', 440, 440, 'shelekemir', 43.5136986, '901', '3', 76.8909988, '902', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22908, 'Кордай', 451, 451, 'kordaj', 43.050499, '1063', '3', 74.7152023, '1064', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22909, 'Отар', 452, 452, 'otar', 43.5349007, '1055', '3', 75.2129974, '1056', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22910, 'Даулет', 453, 453, 'daulet', 43.6018982, '641', '3', 77.1023026, '642', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22911, 'Куш', 454, 454, 'kush', 43.4869995, '777', '3', 77.4616013, '778', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22912, 'Остемир', 457, 457, 'ostemir', 43.6285019, '813', '3', 77.2556992, '814', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22913, 'Сункар', 459, 459, 'sunkar', 44.1216011, '833', '3', 77.1099014, '834', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22914, 'Аксукент', 467, 467, 'аksukent', 42.4407997, '1447', '3', 69.8495026, '1448', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22915, 'Ельтай', 471, 471, 'el''taj', 46.8919983, '639', '3', 81.5966034, '640', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22916, 'Индер', 477, 477, 'inder', 48.5326996, '933', '3', 51.7532997, '934', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22917, 'Махамбет', 479, 479, 'mahambet', 47.6682014, '939', '3', 51.5854988, '940', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22918, 'Качар', 481, 481, 'kachar', 53.3681984, '1247', '3', 62.8647003, '1248', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22919, 'Шалкар', 545, 545, 'alm-shalkar', 43.2000999, '899', '3', 76.5615997, '900', 'CITY', '17');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22920, 'Новая Бухтарма', 547, 547, 'novaja buhtarma', 49.6264992, '1001', '3', 83.5223007, '1002', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22921, 'Бозайгыр', 549, 549, 'bozajgyr', 51.4720001, '333', '3', 71.2068024, '334', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22922, 'Осакаровка', 551, 551, 'osakarovka', 50.5648003, '1179', '3', 72.5683975, '1180', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22923, 'Коккайнар', 557, 557, 'kokkajnar', 43.294899, '739', '3', 76.8411026, '740', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22924, 'Тонкерис', 559, 559, 'tonkeris', 43.4747009, '859', '3', 77.1371002, '860', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22925, 'Жаугашты', 593, 593, 'zhaugashty', 43.3918991, '653', '3', 76.6667023, '654', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22926, 'Каракемер', 597, 597, 'karakemer', 43.4239006, '717', '3', 77.6231995, '718', 'CITY', '17');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22927, 'Кушмурун', 601, 601, 'kushmurun', 52.4646988, '1255', '3', 64.4714966, '1256', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22928, 'Затобольск', 603, 603, 'zatobol''sk', 53.2005997, '1241', '3', 63.6918983, '1242', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22929, 'Кумтоган', 627, 627, 'kumtogan', 43.1525002, '775', '3', 75.9253998, '776', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22930, 'Балпык Би', 629, 629, 'balpik-bi', 45.0085983, '605', '3', 78.3961029, '606', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22931, 'Коянды', 637, 637, 'kojandy', 51.2742996, '397', '3', 71.6427002, '398', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22932, 'Шенгельды', 643, 643, 'shengel''dy', 43.9799995, '903', '3', 77.4626007, '904', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22933, 'Жалпаксай', 665, 665, 'zhalpaksaj', 43.232399, '659', '3', 76.6791992, '660', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22934, 'Жибек жолы', 667, 667, 'zhibek zholy', 51.7882996, '361', '3', 69.4076004, '362', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22935, 'Каракастек', 675, 675, 'karakastek', 43.1285019, '715', '3', 76.1007996, '716', 'CITY', '17');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22936, 'Еркин', 677, 677, 'erkin', 43.419899, '637', '3', 76.9829025, '638', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22937, 'Форт-Шевченко', 679, 679, 'fort-shevchenko', 44.5074997, '1347', '3', 50.2627983, '1348', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22938, 'Баутино', 681, 681, 'Bautino', 44.5442009, '1321', '3', 50.2723999, '1322', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22939, 'Аташ', 683, 683, 'Atash', 43.780899, '1317', '3', 51.0798988, '1318', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22940, 'Акшукур', 685, 685, 'akshukur', 43.7820015, '1313', '3', 51.0721016, '1314', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22941, 'С.Шапагатова', 687, 687, 's. shapagatova', 43.8342018, '1343', '3', 53.3526993, '1344', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22942, 'Кайнар', 689, 689, 'kajnar', 43.3875999, '701', '3', 76.8738022, '702', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22943, 'Мерке', 693, 693, 'merke', 42.8610992, '1067', '3', 73.1763, '1068', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22944, 'Курык', 695, 695, 'kuryk', 42.9468002, '1333', '3', 72.7434998, '1334', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22945, 'Толкын', 697, 697, 'tolkyn', 43.6296997, '855', '3', 77.8937988, '856', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22946, 'Шубаркудук', 699, 699, 'shubarkuduk', 49.1459999, '523', '3', 56.486599, '524', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22947, 'Коянкус', 725, 725, 'kojankus', 43.3949013, '767', '3', 76.9425964, '768', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22948, 'Федоровка', 727, 727, 'fedorovka', 52.7318001, '1285', '3', 62.1472015, '1286', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22949, 'Аулиеколь', 729, 729, 'auliekol''', 52.3932991, '1221', '3', 63.7821007, '1222', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22950, 'Косшы', 731, 731, 'kosshy', 50.9743996, '393', '3', 71.3520966, '394', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22951, 'Ульгили', 733, 733, 'ul''gili', 43.202301, '879', '3', 76.4284973, '880', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22952, 'Улкен Нарын', 735, 735, 'ulken naryn', 49.2131004, '1027', '3', 84.5106964, '1028', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22953, 'Катон-Карагай', 737, 737, 'katon-karagaj', 50.4109001, '987', '3', 80.2640991, '988', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22954, 'Имантау', 739, 739, 'imantau', 52.9744987, '1409', '3', 68.3622971, '1410', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22955, 'Наурыз', 757, 757, 'nauryz', 43.1850014, '805', '3', 76.6512985, '806', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22956, 'Тобол', 759, 759, 'tobol', 53.0625992, '1277', '3', 62.5330009, '1278', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22957, 'Дубовка', 761, 761, 'dubovka', 49.9115982, '1157', '3', 72.8487015, '1158', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22958, 'Кырбалтабай', 767, 767, 'kyrbaltabaj', 43.4026985, '791', '3', 77.240303, '792', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22959, 'Булакты', 769, 769, 'bulakty', 49.1805992, '631', '3', 58.4194984, '632', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22960, 'Фарфоровое', 771, 771, 'farforovoe', 51.3381004, '455', '3', 71.0931015, '456', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22961, 'Кызылсуат', 775, 775, 'kyzylsuat', 50.8893013, '405', '3', 71.8728027, '406', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22962, 'Белоусовка', 781, 781, 'belousovka', 50.0688019, '965', '3', 82.3925018, '966', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22963, 'Наурызбайский р-н', 783, 783, 'nauryzbajskiy', 43.1892014, '257', '3', 76.8169022, '284', 'DISTRICT', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22964, 'Исаево', 787, 787, 'isaevo', 43.3330994, '693', '3', 76.7779999, '694', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22965, 'Кобетей', 789, 789, 'kobetej', 49.9123993, '1167', '3', 72.5314026, '1168', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22966, 'Урджар', 791, 791, 'urdzhar', 47.7677994, '1029', '3', 81.5199966, '1030', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22967, 'Шульбинск', 793, 793, 'shul''binsk', 50.1352997, '1039', '3', 81.1801987, '1040', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22968, 'Бейнеу', 795, 795, 'bejneu', 45.3167, '1327', '3', 55.2000008, '1328', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22969, 'Асы-Сага', 815, 815, 'asy-saga', 43.4669991, '583', '3', 78.3459015, '584', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22970, 'Заречное', 817, 817, 'kost-zarechnoe', 53.2837982, '1239', '3', 63.8306999, '1240', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22971, 'Жукей', 819, 819, 'zhukej', 53.1615982, '365', '3', 70.1980972, '366', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22972, 'Воздвиженка', 881, 881, 'vozdvizhenka', 51.2116013, '341', '3', 71.1137009, '342', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22973, 'Байконур', 885, 885, 'bajkonur', 0, '1293', '3', 0, '1296', 'CITY', '0');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22975, 'Виноградовка', 983, 983, 'vinogradovka', 54.7425995, '1405', '3', 69.8391037, '1406', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22976, 'Койшыбек', 985, 985, 'kojshybek', 43.3390999, '735', '3', 77.4377975, '736', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22977, 'Шетпе', 989, 989, 'shetpe', 44.4729996, '1349', '3', 51.4990997, '1350', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22978, 'Тескенсу', 1047, 1047, 'teskensu', 43.3109016, '853', '3', 76.9107971, '854', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22979, 'Бактыбая Жолбарысулы', 1053, 1053, 'baktybaya-zholbarysuly', 44.9269981, '603', '3', 78.4003983, '604', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22980, 'Тала', 1079, 1079, 'tala', 35.2146988, '2028', '2', 33.6287994, '2029', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22981, 'Байбулак', 1085, 1085, 'bajbulak', 45.2150993, '593', '3', 77.3393021, '594', 'CITY', '5');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22982, 'Сарыколь', 1129, 1129, 'sarykol', 52.8666992, '1271', '3', 63.4877014, '1272', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22983, 'Карабулак', 1135, 1135, 'uko-karabulak', 42.0834007, '1461', '3', 69.8559036, '1462', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22984, 'Глубокое', 1153, 1153, 'glubokoe', 49.5489006, '975', '3', 83.6485977, '976', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22985, 'Коктерек', 1177, 1177, 'kokterek', 43.4785004, '751', '3', 77.0022964, '752', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22986, 'Куйгенжар', 1179, 1179, 'kujgenzhar', 51.0032005, '403', '3', 71.5465012, '404', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22987, 'Катарколь', 1181, 1181, 'katarkol', 52.2549019, '381', '3', 70.6692963, '382', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22988, 'Денисовка', 1205, 1205, 'denisovka', 53.0456009, '1231', '3', 63.9799004, '1232', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22989, 'Кольащы', 1217, 1217, 'kolashy', 43.3596992, '755', '3', 76.6159973, '756', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22990, 'им. Касыма Кайсенова', 1219, 1219, 'im.-kasyma-kajsenova', 48.8246994, '989', '3', 81.2285004, '990', 'CITY', '5');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22991, 'Сарыкемер', 1229, 1229, 'sarykemer', 42.8540993, '1071', '3', 71.1682968, '1072', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22992, 'Узунколь', 1277, 1277, 'uzunkol', 50.9514008, '453', '3', 71.7963028, '454', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22993, 'Узунколь', 1279, 1279, 'uzunkoll', 52.7751007, '1283', '3', 63.615799, '1284', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22994, 'Екпенды', 1281, 1281, 'ekpendy', 43.3622017, '643', '3', 76.6579971, '644', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22995, 'Ботакара', 1283, 1283, 'botakara', 49.7619019, '1155', '3', 72.8665009, '1156', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22996, 'Камысты', 1289, 1289, 'kamysty', 53.0928001, '1243', '3', 63.3833008, '1244', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22997, 'Улытау', 1291, 1291, 'ulytau', 49.5919991, '1199', '3', 72.9049988, '1200', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22998, 'Каратобе', 1311, 1311, 'karatobe', 49.9323006, '1107', '3', 50.6645012, '1108', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (22999, 'Владимировка', 1313, 1313, 'vladimirovka', 52.5542984, '1227', '3', 62.5517006, '1228', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23000, 'Рахымжана Кошкарбаева', 1315, 1315, 'raxymzhana-koshkarbaeva', 50.9389992, '427', '3', 71.2324982, '428', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23001, 'Сергеевка', 1317, 1317, 'sergeevka', 54.1446991, '1425', '3', 69.2093964, '1426', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23002, 'Енбек', 1319, 1319, 'enbek', 43.4762001, '647', '3', 77.4643021, '648', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23003, 'Боровской', 1323, 1323, 'borovskoj', 52.4864006, '1225', '3', 62.5619011, '1226', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23004, 'Надеждинка', 1329, 1329, 'nadezhdinka', 53.7542, '1265', '3', 63.7438011, '1266', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23005, 'Доссор', 1333, 1333, 'dossor', 47.2370987, '927', '3', 51.6104012, '928', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23006, 'Агадырь', 1335, 1335, 'agadyr', 49.5074997, '1145', '3', 72.927597, '1146', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23007, 'Аккайнар', 1341, 1341, 'akkainar', 43.6888008, '555', '3', 77.273201, '556', 'MICRODISTRICT', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23008, 'Алга', 1343, 1343, 'algas', 43.4155006, '565', '3', 77.186203, '566', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23009, 'Кулан', 1345, 1345, 'kulan', 42.9840012, '1065', '3', 72.6386032, '1066', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23010, 'Ульяновское', 1347, 1347, 'ulyanovskoe', 53.0625992, '1281', '3', 62.5330009, '1282', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23011, 'Тюлькубас', 1355, 1355, 'tyulkubasskiy-raion', 42.5489998, '1493', '3', 70.1568985, '1494', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23012, 'Тарановское', 1363, 1363, 'taranovskoe', 52.8064995, '1275', '3', 63.467701, '1276', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23013, 'Мичуринское', 1365, 1365, 'michurinskoe', 52.6824989, '1261', '3', 63.3620987, '1262', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23014, 'Алмалык', 1367, 1367, 'almalyk', 43.2887001, '573', '3', 77.2291031, '574', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23015, 'Каражар', 1373, 1373, 'karazhar', 51.8849983, '377', '3', 69.8575974, '378', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23016, 'Байзак', 1377, 1377, 'bajzak', 42.9788017, '1053', '3', 71.5239029, '1054', 'DISTRICT', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23017, 'Нияз', 1381, 1381, 'niyaz', 43.4668999, '807', '3', 77.4077988, '808', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23018, 'Актас', 1385, 1385, 'aktas1', 43.3787003, '557', '3', 77.2385025, '558', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23019, 'Кокпекты', 1389, 1389, 'kokpekty', 48.9737015, '1171', '3', 71.6043015, '1172', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23020, 'Кызылту', 1393, 1393, 'kyzyltu', 43.3501015, '787', '3', 77.0820999, '788', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23021, 'Мартук', 1395, 1395, 'martuk', 49.9569016, '503', '3', 57.0014992, '504', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23022, 'Караоткель', 1397, 1397, 'karaotkel', 51.0904999, '379', '3', 71.1747971, '380', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23023, 'Айганым', 1399, 1399, 'ajganym', 43.3605995, '545', '3', 77.004303, '546', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23024, 'Бастобе', 1401, 1401, 'bastobe', 43.9584007, '609', '3', 76.1828995, '610', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23025, 'Коктобе', 1403, 1403, 'koktobe', 51.9453011, '1367', '3', 76.1308975, '1368', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23026, 'Коктобе', 1405, 1405, 'kokto', 43.3745003, '749', '3', 77.4738998, '750', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23027, 'Черноярка', 1407, 1407, 'chernoyarka', 52.0808983, '1389', '3', 76.7461014, '1390', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23028, 'Кендала', 1409, 1409, 'kendala', 43.3889008, '731', '3', 77.1615982, '732', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23029, 'Баскудук', 1411, 1411, 'baskuduk', 43.5096016, '1319', '3', 52.1062012, '1320', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23030, 'Аксаринский кордон', 1413, 1413, 'aksarinskij-kordon', 50.4371986, '963', '3', 80.3139038, '964', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23031, 'Мунайши', 1415, 1415, 'munajshi', 43.4898987, '1339', '3', 52.0836983, '1340', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23032, 'Мичурино', 1417, 1417, 'michurino', 51.2535019, '1117', '3', 51.3665009, '1118', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23033, 'Кокдала', 1419, 1419, 'kokdala', 45.2081985, '737', '3', 78.1149979, '738', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23034, 'Курайлы', 1421, 1421, 'kurajly', 50.4193993, '495', '3', 57.0852013, '496', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23035, 'Жалпактобе', 1423, 1423, 'zhalpaktobe', 42.8418007, '1059', '3', 71.4503021, '1060', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23036, 'Красный яр', 1425, 1425, 'krasnyj-yar', 53.2398987, '401', '3', 69.5334015, '402', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23037, 'Батыр', 1427, 1427, 'batyr', 43.6688004, '1323', '3', 51.2285004, '1324', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23038, 'Кызылтобе', 1429, 1429, 'kyzyltobe', 0, '1337', '3', 0, '1338', 'CITY', '0');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23039, 'Шайкорык', 1431, 1431, 'shajkoryk', 43.546299, '1077', '3', 71.1119003, '1078', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23040, 'Тайтобе', 1433, 1433, 'tajtobe', 50.5862007, '439', '3', 71.0194016, '440', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23041, 'Пригородный', 1437, 1437, 'prigorodnyj', 50.4328995, '509', '3', 57.1591988, '510', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23042, 'Садовое', 1439, 1439, 'sadovoe', 50.3675995, '511', '3', 57.7056999, '512', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23043, 'Пугачево', 1443, 1443, 'pugachevo', 50.5452995, '1121', '3', 50.9500999, '1122', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23044, 'Акжар', 1445, 1445, 'akzharа', 47.3641014, '913', '3', 52.4040985, '914', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23045, 'Еркинкала', 1447, 1447, 'erkinkala', 47.4566994, '929', '3', 51.8120003, '930', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23046, 'Майкайын', 1449, 1449, 'majkajyn', 52.2970009, '1373', '3', 77.0098038, '1374', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23047, 'Успеноюрьевка', 1453, 1453, 'uspenoyurevka', 52.6358986, '451', '3', 70.3867035, '452', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23048, 'Деркул', 1455, 1455, 'derkul', 50.6711006, '1101', '3', 51.2578011, '1102', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23049, 'Федоровка', 1457, 1457, 'fedorovka1', 51.1353989, '1129', '3', 51.0556984, '1130', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23050, 'Тасбогет', 1459, 1459, 'tasboget', 44.6534004, '1307', '3', 65.4449005, '1308', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23051, 'Антоновка', 1461, 1461, 'antonovka', 51.7979012, '1217', '3', 64.2655029, '1218', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23052, 'Аккайнар', 1463, 1463, 'akkajnar', 49.7126999, '957', '3', 82.0379028, '958', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23053, 'Чапаево', 1465, 1465, 'chapaevo1', 43.4706993, '891', '3', 76.7751999, '892', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23054, 'Белес', 1473, 1473, 'beles', 50.7508011, '1097', '3', 50.8236008, '1098', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23055, 'Герасимовка', 1477, 1477, 'gerasimovka', 49.9306984, '973', '3', 82.2774963, '974', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23056, 'Восточный', 1479, 1479, 'vostochnyj', 49.5494003, '971', '3', 82.6137009, '972', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23057, 'Зачаганск', 1481, 1481, 'zachagansk', 50.3278008, '1103', '3', 50.763401, '1104', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23058, 'Ракуша', 1483, 1483, 'rakusha', 47.3641014, '943', '3', 52.2832985, '944', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23059, 'Шидерты', 1485, 1485, 'shiderty', 52.3507996, '1393', '3', 77.1635971, '1394', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23060, 'Бобровка', 1487, 1487, 'bobrovka', 49.0707016, '967', '3', 81.5580978, '968', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23061, 'Казахстан', 1489, 1489, 'kazaxstan', 43.9124985, '705', '3', 77.3713989, '706', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23062, 'Новый', 1493, 1493, 'novyj', 43.5004997, '809', '3', 77.0094986, '810', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23063, 'Кызылжулдуз', 1495, 1495, 'kyzylzhulduz', 53.3042984, '407', '3', 68.8563995, '408', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23064, 'Мангышлак', 1497, 1497, 'mangyshlak', 44.0887985, '1335', '3', 52.986599, '1336', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23065, 'Тельмана', 1499, 1499, 'telmana', 51.4546013, '447', '3', 70.3300018, '448', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23066, 'Алга', 1501, 1501, 'alga1', 43.4985008, '1049', '3', 75.1999969, '1050', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23067, 'Дружба', 1503, 1503, 'druzhba', 52.4967995, '1233', '3', 65.0823975, '1234', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23068, 'Махамбетов', 1505, 1505, 'maxambetov', 45.4160004, '1305', '3', 64.1895981, '1306', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23069, 'Баянды', 1507, 1507, 'bayandy', 44.1699982, '1325', '3', 54.1362, '1326', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23070, 'Таскен', 1509, 1509, 'tasken', 43.9031982, '1485', '3', 68.6837006, '1486', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23071, 'Толе би', 1511, 1511, 'tole-bi', 43.7354012, '857', '3', 76.5076981, '858', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23072, 'Алтынтобе', 1513, 1513, 'altyntobe', 44.1478004, '1449', '3', 68.7149963, '1450', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23073, 'Шиен', 1515, 1515, 'shien', 43.8396988, '1079', '3', 71.3557968, '1080', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23074, 'Алтын-Дала', 1517, 1517, 'altyn-dala', 53.5223007, '1223', '3', 64.4896011, '1224', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23075, 'Коктерек', 1519, 1519, 'kokterek1', 49.3442993, '991', '3', 81.733902, '992', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23076, 'Шымбулак', 1521, 1521, 'shymbulak', 43.3291016, '907', '3', 77.2130966, '908', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23077, 'Кызылжар', 1523, 1523, 'kyzylzhar1', 49.9935989, '499', '3', 56.9273987, '500', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23078, 'Жетыбай', 1525, 1525, 'zhetybaj', 43.9005013, '1331', '3', 54.6856003, '1332', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23079, 'Успенка', 1537, 1537, 'uspenka', 51.8366013, '1391', '3', 76.1747971, '1392', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23080, 'Дамса', 1539, 1539, 'damsa', 52.2438011, '347', '3', 70.9891968, '348', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23081, 'Коктас', 1541, 1541, 'koktas', 48.4580002, '1169', '3', 75.8464966, '1170', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23082, 'Али', 1543, 1543, 'ali', 43.986599, '567', '3', 77.615303, '568', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23083, 'Ленинский', 1545, 1545, 'leninskij', 52.3238983, '1369', '3', 76.7900009, '1370', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23084, 'Шахтерское', 1547, 1547, 'shaxterskoe', 48.6477013, '1207', '3', 75.6339035, '1208', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23085, 'Жилянка', 1549, 1549, 'zhilyanka', 49.6344986, '485', '3', 57.1032982, '486', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23086, 'Болек', 1551, 1551, 'bolek', 43.7686996, '627', '3', 77.504097, '628', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23087, 'Дамба', 1553, 1553, 'damba', 47.394001, '925', '3', 52.4151001, '926', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23088, 'Уштерек', 1555, 1555, 'ushterek', 45.6174011, '889', '3', 77.3612976, '890', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23089, 'Ынтымак', 1559, 1559, 'yntymak2', 43.0136986, '1503', '3', 68.7919006, '1504', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23090, 'Кутарыс', 1561, 1561, 'kutarys', 42.8279991, '1465', '3', 69.3302994, '1466', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23091, 'Волгодоновка', 1563, 1563, 'volgodonovka', 53.0443993, '343', '3', 70.0113983, '344', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23092, 'Ахмерово', 1567, 1567, 'axmerovo', 49.0852013, '959', '3', 81.4701996, '960', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23093, 'Челгаши', 1569, 1569, 'chelgashi', 53.050499, '1287', '3', 64.6799011, '1288', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23094, 'Орнек', 1571, 1571, 'ornek', 44.1055984, '1069', '3', 71.4726028, '1070', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23095, 'Алмалы', 1573, 1573, 'almaly1', 47.5876007, '917', '3', 52.8325996, '918', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23096, 'Топиха', 1575, 1575, 'topixa', 49.2436981, '1025', '3', 81.7778015, '1026', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23097, 'Трекино', 1577, 1577, 'trekino', 50.3067017, '1125', '3', 50.6315002, '1126', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23098, 'Мынбаев', 1579, 1579, 'mynbaev1', 48.3893013, '1173', '3', 75.1411972, '1174', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23099, 'Сортировка', 1581, 1581, 'sortirovka', 49.0113983, '1191', '3', 71.1388016, '1192', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23100, 'Ушановский', 1583, 1583, 'ushanovskij', 48.9696007, '1031', '3', 81.6679993, '1032', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23101, 'Баянаул', 1585, 1585, 'bayanaul', 51.0186996, '1357', '3', 77.4352036, '1358', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23102, 'Подстепное', 1589, 1589, 'podstepnoe', 50.4191017, '1119', '3', 50.9062004, '1120', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23103, 'Сарыжар', 1591, 1591, 'saryzhar', 49.5693016, '515', '3', 57.2123985, '516', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23104, 'Ильинка', 1593, 1593, 'ilinka', 51.2481995, '369', '3', 71.9120026, '370', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23105, 'Красный Кордон', 1595, 1595, 'krasnyj-kordon', 52.3111992, '399', '3', 71.2309036, '400', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23106, 'Коктума', 1597, 1597, 'koktuma', 45.9160004, '753', '3', 81.4850006, '754', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23107, 'Жомарт', 1599, 1599, 'zhomart', 43.5931015, '687', '3', 77.0660019, '688', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23108, 'Теренкара', 1601, 1601, 'terenkara', 44.3702011, '851', '3', 78.4378967, '852', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23109, 'Куленовка', 1603, 1603, 'kulenovka', 49.3442993, '993', '3', 81.4263, '994', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23110, 'Первомайский', 1605, 1605, 'pervomajskij', 49.3442993, '1007', '3', 81.8436966, '1008', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23111, 'Мырзакент', 1609, 1609, 'myrzakent', 42.2022018, '1479', '3', 69.0366974, '1480', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23112, 'Луганск', 1611, 1611, 'lugansk', 52.1484985, '1371', '3', 76.9878006, '1372', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23113, 'Головацкий', 1613, 1613, 'golovaczkij', 44.2294006, '635', '3', 79.8795013, '636', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23114, 'Кенесары', 1615, 1615, 'kenesary', 51.4553986, '383', '3', 70.7286987, '384', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23115, 'Московское ', 1617, 1617, 'moskovskoe-', 52.0965996, '1263', '3', 64.5072021, '1264', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23116, 'Павлодарское', 1621, 1621, 'pavlodarskoe', 52.3373985, '1377', '3', 77.0756989, '1378', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23117, 'Жетысу', 1623, 1623, 'zhetysu', 44.6769981, '685', '3', 78.2296982, '686', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23118, 'Кокибель', 1625, 1625, 'kokibel', 43.2910004, '1469', '3', 72.6176987, '1470', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23119, 'Малотимофеевка', 1627, 1627, 'malotimofeevka', 51.2067986, '415', '3', 71.5384979, '416', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23120, 'Ямышево', 1629, 1629, 'yamyshevo', 52.4718018, '1375', '3', 77.0317001, '1376', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23121, 'Асыката', 1631, 1631, 'asykata', 43.5914001, '1453', '3', 69.3082962, '1454', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23122, 'Акбулак', 1635, 1635, 'akbulak1', 52.1357994, '321', '3', 70.0003967, '322', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23123, 'Зарослое', 1637, 1637, 'zarosloe', 54.5363007, '1407', '3', 68.9347, '1408', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23124, 'Альжан', 1639, 1639, 'alzhan', 0, '1399', '3', 0, '1400', 'CITY', '0');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23125, 'Жетекши', 1641, 1641, 'zhetekshi', 52.4645996, '1359', '3', 77.6697006, '1360', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23126, 'Уваковское', 1645, 1645, 'uvakovskoe', 54.5425987, '1431', '3', 68.8687973, '1432', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23127, 'Тельманово', 1647, 1647, 'telmanovo', 54.8086014, '1429', '3', 69.3684998, '1430', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23128, 'Шакаман', 1649, 1649, 'shakaman', 50.6250992, '1033', '3', 80.6889038, '1034', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23129, 'Прииртышское', 1651, 1651, 'priirtyshskoe', 52.3642998, '1381', '3', 77.2954025, '1382', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23130, 'Арай', 1653, 1653, 'araj', 43.1212006, '579', '3', 77.3451996, '580', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23131, 'Приречное', 1655, 1655, 'prirechnoe', 52.4724998, '425', '3', 70.6815033, '426', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23132, 'Сазда', 1657, 1657, 'sazda', 49.8807983, '513', '3', 57.6049004, '514', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23133, 'Михайловка', 1659, 1659, 'mixajlovka', 51.7705994, '1259', '3', 64.3753967, '1260', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23134, 'Отрадное', 1661, 1661, 'otradnoe', 52.6422997, '423', '3', 71.3080978, '424', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23135, 'Солнечный', 1663, 1663, 'solnechnyj', 52.229599, '1383', '3', 77.3394012, '1384', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23136, 'Ганюшкино', 1665, 1665, 'ganyushkino', 47.5205994, '923', '3', 52.4151001, '924', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23137, 'Ивановка', 1667, 1667, 'ivanovka', 49.774601, '985', '3', 83.0221024, '986', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23138, 'Улгили', 1669, 1669, 'ulgili', 43.9258003, '1495', '3', 69.1545029, '1496', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23139, 'Састобе', 1671, 1671, 'sastobe', 43.2069016, '1481', '3', 68.7590027, '1482', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23140, 'им. Турара Рыскулова', 1673, 1673, 'im.-turara-ryskulova', 42.8711014, '1459', '3', 69.3421021, '1460', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23141, 'Шакпак баба', 1675, 1675, 'shakpak-baba', 43.463501, '1499', '3', 68.9567032, '1500', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23142, 'им.Балгабека Кыдырбекулы', 1677, 1677, 'im.balgabeka-kydyrbekuly', 44.2857018, '691', '3', 77.8585968, '692', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23143, 'Береке (Память Ильича)', 1679, 1679, 'bereke-(pamyat-ilicha)', 47.6767006, '919', '3', 52.6787987, '920', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23144, 'Тарханка', 1681, 1681, 'tarxanka', 49.2436981, '1023', '3', 83.7526016, '1024', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23145, 'Дарьинск', 1683, 1683, 'darinsk', 50.3208008, '1099', '3', 50.7743988, '1100', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23146, 'Ново-Явленка', 1685, 1685, 'novo-yavlenka', 49.0707016, '1003', '3', 81.6899033, '1004', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23147, 'Парамоновка', 1687, 1687, 'paramonovka', 52.3103981, '1379', '3', 76.1968002, '1380', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23148, 'Ахмет', 1691, 1691, 'axmet', 48.1926994, '1149', '3', 75.4916, '1150', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23149, 'Тамды', 1693, 1693, 'tamdy', 43.4385986, '1073', '3', 71.2988968, '1074', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23150, 'Жаскешу', 1695, 1695, 'zhaskeshu', 43.655201, '1455', '3', 69.6379013, '1456', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23151, 'Меновное', 1699, 1699, 'menovnoe', 49.6887016, '999', '3', 81.8740005, '1000', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23152, 'Акжол', 1703, 1703, 'akzhol', 43.7350006, '1445', '3', 69.2643967, '1446', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23153, 'Кулан', 1705, 1705, 'kulan1', 42.3123016, '1473', '3', 69.2417984, '1474', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23154, 'Казылжарма', 1707, 1707, 'kazylzharma', 45.3580017, '1301', '3', 63.6063995, '1302', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23155, 'Жанаконыс', 1709, 1709, 'zhanakonys', 49.4333, '483', '3', 57.4211006, '484', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23156, 'Малый Чаган', 1713, 1713, 'malyj-chagan', 50.0881004, '1115', '3', 50.7523994, '1116', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23157, 'Умирзак', 1715, 1715, 'umirzak', 44.0909004, '1345', '3', 54.0703011, '1346', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23158, 'Кенжеколь', 1717, 1717, 'kenzhekol', 52.1890984, '1365', '3', 76.9878006, '1366', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23159, 'Озерное', 1719, 1719, 'ozernoe', 51.7024002, '1267', '3', 64.2216034, '1268', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23160, 'Новодолинск', 1721, 1721, 'novodolinsk', 48.0940018, '1177', '3', 74.8125, '1178', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23161, 'Алатау', 1723, 1723, 'alatau', 44.4135017, '1047', '3', 72.6042023, '1048', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23162, 'Акдала', 1725, 1725, 'akdala', 44.7159004, '553', '3', 77.1855011, '554', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23163, 'Заря', 1727, 1727, 'zarya', 52.0808983, '1361', '3', 77.1195984, '1362', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23164, 'Убаган', 1729, 1729, 'ubagan', 51.8931007, '1279', '3', 64.1557007, '1280', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23165, 'Амангельды', 1735, 1735, 'amangeldy', 43.4127998, '577', '3', 77.097702, '578', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23166, 'Уштобе', 1745, 1745, 'karagandinskaja-oblast-ushtobe', 45.2563019, '1201', '3', 77.9731979, '1202', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23167, 'Жамбыл', 1747, 1747, 'kostanajskaja-oblast-zhambyl', 53.287899, '1235', '3', 63.6813011, '1236', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23168, 'Жиланды', 1749, 1749, 'zhilandy', 47.8373985, '979', '3', 80.3780975, '980', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23170, 'Тассай', 1753, 1753, 'tassaj', 42.3064995, '1487', '3', 69.0851974, '1488', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23171, 'Пригородное', 1755, 1755, 'prigorodnoe', 48.9117012, '1009', '3', 82.0195007, '1010', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23172, 'Талгайран', 1757, 1757, 'talgajran', 47.3492012, '945', '3', 52.4151001, '946', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23173, 'Березняки', 1759, 1759, 'bereznyaki', 48.4459991, '1153', '3', 75.7792969, '1154', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23174, 'Шашубай', 1763, 1763, 'shashubaj', 48.2849998, '1209', '3', 75.1201019, '1210', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23175, 'Чайкино', 1765, 1765, 'chajkino', 54.073101, '457', '3', 68.1378021, '458', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23176, 'Круглоозерное', 1769, 1769, 'krugloozernoe', 50.2995987, '1109', '3', 50.763401, '1110', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23177, 'Карасу', 1773, 1773, 'karasu', 45.0115013, '1463', '3', 68.7590027, '1464', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23178, 'Чайкурук', 1777, 1777, 'chajkuruk', 44.0500984, '1085', '3', 70.9782028, '1086', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23179, 'Шолаккаргалы', 1779, 1779, 'sholakkargaly', 44.2349014, '1081', '3', 71.2455978, '1082', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23180, 'Мартобе', 1781, 1781, 'martobe', 44.7302017, '1477', '3', 69.4181976, '1478', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23181, 'Аймен', 1783, 1783, 'ajmen', 43.5211983, '547', '3', 77.1920013, '548', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23182, 'Актоган', 1785, 1785, 'aktogan', 43.7086983, '559', '3', 77.5053024, '560', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23183, 'Аксай', 1787, 1787, 'aksaj1', 47.4238014, '915', '3', 52.360199, '916', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23184, 'Жумыскер', 1789, 1789, 'zhumysker', 47.4611015, '931', '3', 52.448101, '932', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23185, 'Прапорщиково', 1791, 1791, 'praporshikovo', 49.3730011, '1011', '3', 81.3823013, '1012', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23186, 'Маржанбулак', 1793, 1793, 'marzhanbulak', 49.3903008, '501', '3', 57.0586014, '502', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23187, 'Горная Ульбинка', 1795, 1795, 'gornaya-ulbinka', 49.2723999, '977', '3', 81.2944031, '978', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23188, 'Сабынды', 1797, 1797, 'sabyndy', 51.8917999, '429', '3', 71.2748032, '430', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23189, 'Бирлик', 1799, 1799, 'birlik2', 47.3790016, '921', '3', 52.3051987, '922', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23190, 'Сарышаган', 1803, 1803, 'saryshagan', 46.1155014, '1187', '3', 73.6175995, '1188', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23191, 'Теренколь', 1805, 1805, 'terenkol', 52.3777008, '1385', '3', 76.9218979, '1386', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23193, 'Атамекен', 1811, 1811, 'atameken', 44.1699982, '1315', '3', 52.9936981, '1316', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23194, 'Досан', 1817, 1817, 'dosan', 44.8916016, '1297', '3', 65.5811005, '1298', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23195, 'Кызылсок', 1819, 1819, 'kyzylsok', 43.2569008, '785', '3', 76.2522964, '786', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23196, 'Красный партизан', 1823, 1823, 'krasnyj-partizan', 53.1297989, '1253', '3', 63.5674019, '1254', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23197, 'Интернациональный', 1825, 1825, 'internaczionalnyj', 51.1245995, '371', '3', 71.5964966, '372', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23198, 'Винное', 1831, 1831, 'vinnoe', 50.0385017, '969', '3', 82.5873032, '970', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23199, 'Коктагай', 1833, 1833, 'koktagaj', 47.5205994, '935', '3', 52.6678009, '936', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23200, 'Шубар', 1837, 1837, 'shubar1', 53.0470009, '461', '3', 70.5507965, '462', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23201, 'Новенький', 1841, 1841, 'novenkij', 50.6707993, '1113', '3', 51.0158005, '1114', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23202, 'Карабулак', 1843, 1843, 'karabulak2', 44.9480019, '711', '3', 78.471199, '712', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23203, 'Радужное', 1845, 1845, 'raduzhnoe', 49.4160004, '1013', '3', 81.8218002, '1014', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23204, 'Томарлы', 1849, 1849, 'tomarly', 47.6915016, '947', '3', 52.5578995, '948', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23205, 'Курашасай', 1851, 1851, 'kurashasaj', 49.332901, '497', '3', 57.3003006, '498', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23206, 'Аккемер', 1853, 1853, 'akkemer', 49.4189987, '479', '3', 57.3223, '480', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23207, 'Чернорецкое', 1855, 1855, 'chernoreczkoe', 52.4718018, '1387', '3', 76.6582031, '1388', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23208, 'Барбастау', 1857, 1857, 'barbastau', 50.9571991, '1095', '3', 51.4159012, '1096', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23209, 'Нурбулак', 1859, 1859, 'nurbulak', 49.8956985, '507', '3', 57.2563019, '508', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23210, 'Лесхоз', 1863, 1863, 'lesxoz', 50.3112984, '997', '3', 83.7305984, '998', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23211, 'Миялы', 1867, 1867, 'miyaly', 47.4760017, '941', '3', 52.5359993, '942', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23212, 'Ясное', 1869, 1869, 'yasnoe', 49.3114014, '527', '3', 57.289299, '528', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23213, 'Заречный', 1871, 1871, 'zarechnyj1', 49.3759995, '487', '3', 57.2783012, '488', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23214, 'Константиновка', 1875, 1875, 'konstantinovka', 50.9718018, '389', '3', 72.0733032, '390', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23215, 'Акыртобе', 1877, 1877, 'akyrtobe', 44.1767998, '1045', '3', 71.2857971, '1046', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23216, 'Черноводск', 1879, 1879, 'chernovodsk', 43.8623009, '1497', '3', 68.7149963, '1498', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23217, 'Киялы', 1883, 1883, 'kiyaly', 54.5298996, '1411', '3', 68.6270981, '1412', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23218, 'Асан', 1885, 1885, 'asan', 50.1162987, '1093', '3', 50.6206017, '1094', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23219, 'Караменды', 1887, 1887, 'karamendy', 51.6579018, '1245', '3', 64.2292023, '1246', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23220, 'Самарка', 1897, 1897, 'samarka', 48.4168015, '1183', '3', 75.955101, '1184', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23221, 'Коктал', 1899, 1899, 'koktal', 45.6805, '743', '3', 79.2170029, '744', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23222, 'Капал', 1901, 1901, 'kapal', 46.1544991, '707', '3', 78.1743011, '708', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23223, 'Новопавловка', 1903, 1903, 'novopavlovka', 54.4978981, '1417', '3', 69.066597, '1418', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23224, 'Торангалык', 1905, 1905, 'torangalyk', 46.4891014, '1197', '3', 74.5289993, '1198', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33512, 'Кыргызстан', 33511, 279, 'kyrgyzstan', 41.3793983, '1531', '1', 74.1531982, '1540', 'COUNTRY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33514, 'Бишкек', 33513, 280, 'bishkek', 42.8768997, '1532', '2', 74.5968018, '1533', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33516, 'Чолпон-Ата', 33515, 281, 'cholpon-ata', 42.6525002, '1538', '2', 77.0748978, '1539', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33518, 'Россия', 33517, 282, 'rossija', 55.1997986, '1551', '1', 91.6435013, '1644', 'COUNTRY', '2');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33520, 'Москва', 33519, 283, 'moskva', 55.7439003, '1588', '2', 37.5853004, '1589', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33522, 'Санкт-Петербург', 33521, 284, 'sankt-peterburg', 59.9478989, '1614', '2', 30.2203007, '1615', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33524, 'Чехия', 33523, 285, 'chehija', 49.9566994, '2303', '1', 15.1665001, '2308', 'COUNTRY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33526, 'мкр Кокжиек', 33525, 286, 'almaty-zhetysuskij-kokzhiek', 43.3484993, '216', '4', 76.9243011, '217', 'MICRODISTRICT', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33528, 'мкр Орбита-4', 33527, 287, 'almaty-bostandykskij-orbita-4', 43.196701, '198', '4', 76.8775024, '199', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33530, 'мкр Карасу', 33529, 288, 'almaty-alatauskij-mkr-karasu', 43.3455009, '34', '4', 76.9141006, '35', 'MICRODISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33532, 'мкр Дорожник', 33531, 289, 'almaty-zhetysuskij-dorozhnik', 43.3073997, '212', '4', 76.8992996, '213', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33534, 'Болгария', 33533, 292, 'bolgarija', 42.4367981, '1681', '1', 25.0799999, '1724', 'COUNTRY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33536, 'Великобритания', 33535, 293, 'velikobritanija', 54.8093987, '1725', '1', -2.99275994, '1742', 'COUNTRY', '5');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33538, 'Венгрия', 33537, 294, 'vengrija', 47.1921997, '1743', '1', 19.3644009, '1746', 'COUNTRY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33540, 'Таиланд', 33539, 295, 'tailand', 16.3015995, '2189', '1', 101.362, '2196', 'COUNTRY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33542, 'Турция', 33541, 296, 'turcija', 38.5768013, '2197', '1', 34.6464005, '2220', 'COUNTRY', '5');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33544, 'Испания', 33543, 297, 'ispanija', 40.3745003, '1843', '1', -4.28915024, '1960', 'COUNTRY', '5');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33546, 'Греция', 33545, 298, 'grecija', 38.8681984, '1769', '1', 23.0447998, '1790', 'COUNTRY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33548, 'Франция', 33547, 299, 'francija', 47.0351982, '2223', '1', 2.69814992, '2268', 'COUNTRY', '5');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33550, 'Саудовская Аравия', 33549, 300, 'saudovskaja-aravija', 24.2402992, '2151', '1', 44.7098999, '2152', 'COUNTRY', '5');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33552, 'ОАЭ', 33551, 301, 'oae', 24.3003998, '2117', '1', 54.1142006, '2132', 'COUNTRY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33554, 'Италия', 33553, 306, 'italija', 43.6565018, '1961', '1', 11.8804998, '2026', 'COUNTRY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33556, 'Канада', 33555, 307, 'kanada', 67.1047974, '2027', '1', -94.0958023, '2030', 'COUNTRY', '3');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33558, 'США', 33557, 308, 'usa', 39.4978981, '2177', '1', -99.0792007, '2188', 'COUNTRY', '0');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33560, 'Украина', 33559, 310, 'ukraina', 48.4636002, '1655', '1', 31.6860008, '1662', 'COUNTRY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33562, 'Латвия', 33561, 311, 'latvija', 57.3250008, '1541', '1', 24.3743992, '1548', 'COUNTRY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33564, 'мкр Алатау (ИЯФ)', 33563, 317, 'almaty-medeuskij-alatau', 43.3530006, '222', '4', 77.1457977, '223', 'MICRODISTRICT', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33566, 'мкр Мамыр-4', 33565, 319, 'almaty-aujezovskij-mkr-mamyr-4', 43.2126007, '144', '4', 76.8458023, '145', 'MICRODISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33568, 'мкр Мамыр-2', 33567, 320, 'almaty-aujezovskij-mkr-mamyr-2', 43.2126007, '140', '4', 76.8458023, '141', 'MICRODISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33570, 'Святой Влас', 33569, 322, 'svjatoj-vlas', 42.7154999, '1712', '2', 27.7581997, '1713', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33572, 'Сочи', 33571, 323, 'sochi', 43.581501, '1622', '2', 39.7229004, '1623', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33574, 'Кольди', 33573, 339, 'koldi', 43.2955017, '765', '3', 76.6792984, '766', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33576, 'мкр Юбилейный', 33575, 340, 'almaty-medeuskij-jubilejnyj', 43.2061005, '230', '4', 76.9873962, '231', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33578, 'Мерсин', 33577, 342, 'mersin', 36.8041, '2216', '2', 34.6240005, '2217', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33580, 'Германия', 33579, 343, 'germanija', 51.2240982, '1753', '1', 10.5181999, '1768', 'COUNTRY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33582, 'Рига', 33581, 344, 'riga', 56.9462013, '1544', '2', 24.1149998, '1545', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33584, 'Юрмала', 33583, 345, 'jurmala', 56.9487, '1546', '2', 23.7068005, '1547', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33586, 'Аланья', 33585, 347, 'alanja', 36.5443993, '2198', '2', 31.9953995, '2199', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33588, 'Доминиканская Республика', 33587, 349, 'dominikanskaja-respublika', 18.9218998, '1797', '1', -70.9832993, '1802', 'COUNTRY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33590, 'Кипр', 33589, 351, 'kipr', 2, '2031', '1', 33.6016998, '2058', 'COUNTRY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33592, 'Омск', 33591, 352, 'omsk', 54.9892998, '1590', '2', 73.3682022, '1591', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33594, 'мкр Каменское плато', 33593, 354, 'almaty-medeuskij-kamenskoe-plato', 43.1884003, '224', '4', 76.9679031, '225', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33596, 'Краснодар', 33595, 359, 'krasnodar', 45.0238991, '1578', '2', 38.9701996, '1579', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33598, 'мкр Керемет', 33597, 364, 'almaty-bostandykskij-keremet', 43.2360001, '188', '4', 76.938797, '189', 'MICRODISTRICT', '17');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33600, 'мкр Айнабулак-4', 33599, 374, 'almaty-zhetysuskij-ajnabulak-4', 43.3242989, '210', '4', 76.9166031, '211', 'MICRODISTRICT', '17');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33602, 'Израиль', 33601, 375, 'izrail', 30.9067993, '1823', '1', 34.8496017, '1832', 'COUNTRY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33604, 'Иерусалим', 33603, 376, 'ierusalim', 31.7751999, '1826', '2', 35.1988983, '1827', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33606, ' Тель-Авив', 33605, 377, 'tel-aviv', 32.0689011, '1828', '2', 34.7941017, '1829', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33608, 'Хайфа', 33607, 378, 'hajfa', 32.8040009, '1830', '2', 35.0051994, '1831', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33610, 'Калининград', 33609, 379, 'kaliningrad', 54.7074013, '1574', '2', 20.5072994, '1575', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33612, 'мкр Акбулак', 33611, 380, 'almaty-aujezovskij-mkr-akbulak', 43.257, '96', '4', 76.8315964, '97', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33614, 'Старый город', 33613, 382, 'aktobe-staryj-gorod', 50.2827988, '478', '4', 57.1907997, '479', 'DISTRICT', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33616, 'Новый город', 33615, 383, 'aktobe-novyj-gorod', 50.2872009, '474', '4', 57.1713982, '477', 'DISTRICT', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33618, 'Казыбек би р-н', 33617, 384, 'karaganda-kazybek-bi', 49.7929001, '1156', '4', 73.0726013, '1157', 'DISTRICT', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33620, 'Октябрьский р-н', 33619, 385, 'karaganda-oktjabrskij', 49.7969017, '1158', '4', 73.0973969, '1159', 'DISTRICT', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33622, 'Абайский р-н', 33621, 386, 'shymkent-abajskij', 42.2924995, '1456', '4', 69.544899, '1457', 'DISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23226, 'Атасу', 2001, 2001, NULL, 0, NULL, NULL, 0, NULL, 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23228, 'Чарск', 2003, 2003, NULL, 49.3518982, NULL, NULL, 81.0242996, NULL, 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (23225, 'Капшагай', 2000, 2000, NULL, 0, NULL, NULL, 0, NULL, 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33624, 'Аль-Фарабийский р-н', 33623, 387, 'shymkent-al-farabijskij', 42.3227005, '1458', '4', 69.6201019, '1459', 'DISTRICT', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33626, 'Енбекшинский р-н', 33625, 388, 'shymkent-enbekshinskij', 42.3125992, '1460', '4', 69.631897, '1461', 'DISTRICT', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33628, 'Египет', 33627, 389, 'egipet', 26.9148006, '1803', '1', 30.2056999, '1822', 'COUNTRY', '5');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33630, 'Шарм Эль Шейх', 33629, 390, 'sharm-jel-shejh', 27.8873997, '1818', '2', 34.2896996, '1819', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33632, 'Новосибирск', 33631, 402, 'novosibirsk', 55.030201, '1600', '2', 82.9204025, '1601', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33634, 'Азербайджан', 33633, 404, 'azerbajdzhan', 40.3139992, '1527', '1', 47.7322006, '1530', 'COUNTRY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33636, 'Баку', 33635, 405, 'baku', 40.3931007, '1528', '2', 49.8339996, '1529', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33638, 'Киев', 33637, 406, 'kiev', 50.4500999, '1656', '2', 30.5233994, '1657', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33640, 'Ялта', 33639, 407, 'yalta', 44.4952011, '1660', '2', 34.1663017, '1661', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33642, 'Одесса', 33641, 408, 'odessa', 46.4846001, '1658', '2', 30.7325993, '1659', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33644, 'Польша', 33643, 412, 'polska', 52.1945992, '2133', '1', 19.3512001, '2142', 'COUNTRY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33646, 'Варшава', 33645, 413, 'varshava', 52.2503014, '2134', '2', 21.0711994, '2135', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33648, 'Краков', 33647, 414, 'krakov', 50.0704002, '2138', '2', 19.9666004, '2139', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33650, 'Вроцлав', 33649, 415, 'vroclav', 51.0993996, '2136', '2', 17.0126991, '2137', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33652, 'Сербия', 33651, 416, 'serbia', 44.2364998, '2153', '1', 20.7717991, '2156', 'COUNTRY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33654, 'Белград', 33653, 417, 'belgrad', 44.6575012, '2154', '2', 20.7113991, '2155', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33656, 'Португалия', 33655, 422, 'portugalija', 40.0696983, '2143', '1', -8.39694023, '2146', 'COUNTRY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33658, 'Австрия', 33657, 423, 'avstrija', 47.8013992, '1667', '1', 14.6194, '1680', 'COUNTRY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33660, 'Хорватия', 33659, 424, 'horvatija', 45.1400986, '2269', '1', 16.4279995, '2278', 'COUNTRY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33662, 'Швейцария', 33661, 425, 'shvejcarija', 47.0295982, '2309', '1', 8.0715704, '2316', 'COUNTRY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33664, 'Вена', 33663, 427, 'vena', 48.2123985, '1672', '2', 16.3789005, '1673', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33666, 'Капрун', 33665, 428, 'kaprun', 47.2666016, '1676', '2', 12.7545996, '1677', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33668, 'Зальцбург', 33667, 429, 'zalcburg', 47.8420982, '1674', '2', 13.1857004, '1675', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33670, 'Бадгаштайн', 33669, 430, 'badgashtajn', 47.6530991, '1670', '2', 14.8062, '1671', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33672, 'Дубровник', 33671, 431, 'dubrovnik', 42.6427002, '2270', '2', 18.1105995, '2271', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33674, 'Андерматт', 33673, 432, 'andermatt', 46.6329002, '2310', '2', 8.5947504, '2311', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33676, 'Алгарве', 33675, 433, 'algarve', 37.2783012, '2144', '2', -7.16008997, '2145', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33678, 'Новы-Томысль', 33677, 434, 'novy-tomysl', 52.3170013, '2140', '2', 16.1366997, '2141', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33680, 'Измир', 33679, 442, 'izmir', 38.4301987, '2206', '2', 27.1730003, '2207', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33682, 'Тюмень', 33681, 443, 'tjumen', 57.1529999, '1632', '2', 65.5343018, '1633', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33684, 'Черногория', 33683, 444, 'сhernogorija', 42.5536995, '2279', '1', 19.2653008, '2302', 'COUNTRY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33686, 'Анталья', 33685, 445, 'antalja', 36.9211998, '2200', '2', 30.7187004, '2201', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33688, 'Белек', 33687, 446, 'belek', 36.864399, '2202', '2', 31.0713005, '2203', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33690, 'Кемер', 33689, 447, 'kemer', 36.6152992, '2208', '2', 30.4906998, '2209', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33692, 'Стамбул', 33691, 448, 'stambul', 41.0125008, '2218', '2', 28.9134998, '2219', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33694, 'Литва', 33693, 450, 'litva', 55.3816986, '1549', '1', 23.4370003, '1550', 'COUNTRY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33696, 'мкр Мамыр-3', 33695, 455, 'almaty-aujezovskij-mkr mamyr-3', 43.2126007, '142', '4', 76.8458023, '143', 'MICRODISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33698, 'мкр Самал', 33697, 461, 'almaty-medeuskij-samal', 43.2382011, '246', '4', 76.9539032, '247', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33700, 'мкр Думан-2', 33699, 463, 'almaty-medeuskij-duman-2', 43.2803001, '242', '4', 77.001297, '243', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33702, 'Ставрополь', 33701, 465, 'сtavropol', 45.0444984, '1626', '2', 41.969101, '1627', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33704, 'Белгород', 33703, 469, 'belgorod', 50.5974998, '1558', '2', 36.5887985, '1559', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33706, 'мкр Мамыр-1', 33705, 475, 'almaty-aujezovskij-mkr-mamyr-1', 43.2126007, '138', '4', 76.8458023, '139', 'MICRODISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33708, 'Несебыр', 33707, 483, 'nesebyr', 42.6604996, '1702', '2', 27.7141991, '1703', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33710, 'Златни-Пясыци', 33709, 485, 'zlatni-pjasyci', 42.7018013, '1696', '2', 24.8560009, '1697', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33712, 'Созополь', 33711, 487, 'sozopol', 42.4189987, '1716', '2', 27.6938992, '1717', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33714, 'Варна', 33713, 489, 'varna', 43.222599, '1692', '2', 27.8980007, '1693', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33716, 'Поморие', 33715, 491, 'pomorie', 42.5671997, '1710', '2', 27.6187992, '1711', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33718, 'Бяла', 33717, 493, 'bjala', 43.4651985, '1690', '2', 25.7285004, '1691', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33720, 'Солнечный берег', 33719, 495, 'solnechnyj-bereg', 42.6899986, '1720', '2', 27.7141991, '1721', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33722, 'Обзор', 33721, 497, 'obzor', 42.8175011, '1704', '2', 27.8796005, '1705', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33724, 'Бургас', 33723, 499, 'burgas', 42.4985008, '1688', '2', 27.4636002, '1689', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33726, 'Св. Константин и Елена', 33725, 501, 'sv-кonstantin-i-еlena', 43.2316017, '1714', '2', 28.0063992, '1715', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33728, 'Санта-Крус-Де-Тенерифе', 33727, 503, 'santa-rrus-de-tenerife', 40.1800995, '1928', '2', -1.64077997, '1929', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33730, 'Лос-Кристьянос', 33729, 505, 'los-kristjanos', 28.0538998, '1890', '2', -16.7236996, '1891', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33732, 'Эль Медано', 33731, 507, 'el-medano', 28.0470009, '1954', '2', -16.5386009, '1955', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33734, 'Астрахань', 33733, 509, 'astrahan', 46.3479004, '1554', '2', 48.0335999, '1555', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33736, 'Паттайя', 33735, 511, 'pattajja', 12.9347, '2190', '2', 100.902, '2191', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33738, 'Торревьеха', 33737, 513, 'torreveha', 37.9757004, '1944', '2', -0.680617988, '1945', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33740, 'Аликанте', 33739, 515, 'alikante', 39.5460014, '1844', '2', -2.24569011, '1845', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33742, 'Гвардамар-дель-Сегура', 33741, 517, 'gvardamar-del-segura', 38.0867004, '1870', '2', -0.654026985, '1871', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33744, 'Пилар-де-ла-Орадада', 33743, 519, 'pilar-de-la-oradada', 0, '1910', '2', 0, '1911', 'CITY', '0');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33746, 'Сан-Педро-дель-Пинатар', 33745, 521, 'san-pedro-del-pinatar', 37.8340988, '1930', '2', -0.793283999, '1931', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33748, 'Бенидорм', 33747, 523, 'benidorm', 38.5437012, '1856', '2', -0.131827995, '1857', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33750, 'Вильяхойоса', 33749, 525, 'viljahojosa', 38.5057983, '1868', '2', -0.232718006, '1869', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33752, 'Альтеа', 33751, 527, 'altea', 38.5976982, '1848', '2', -0.0485367998, '1849', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33754, 'Будапешт', 33753, 529, 'budapesht', 47.4919014, '1744', '2', 19.0692997, '1745', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33756, 'Афины', 33755, 531, 'afiny', 38.0154991, '1770', '2', 23.7178993, '1771', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33758, 'Рим', 33757, 533, 'rim', 41.8908005, '2008', '2', 12.4959002, '2009', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33760, 'Прага', 33759, 535, 'praga', 50.0663986, '2306', '2', 14.4466, '2307', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33762, 'Узбекистан', 33761, 537, 'uzbekistan', 41.7650986, '1663', '1', 63.1501007, '1666', 'COUNTRY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33764, 'Ташкент', 33763, 539, 'tashkent', 41.3143005, '1664', '2', 69.2673035, '1665', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33766, 'Кампельо', 33765, 541, 'kampelo', 38.4287987, '1880', '2', -0.390758008, '1881', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33768, 'Вильямартин', 33767, 543, 'viljamartin', 36.8604012, '1866', '2', -5.64418983, '1867', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33770, 'Кушадасы', 33769, 553, 'kushadasy', 37.8578987, '2214', '2', 27.2609997, '2215', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33772, 'Индия', 33771, 561, 'indija', 21.2275009, '1833', '1', 77.594101, '1836', 'COUNTRY', '5');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33774, 'Mурудешвара', 33773, 563, 'murudeshvara', 20.8096008, '1834', '2', 78.5641022, '1835', 'CITY', '4');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33776, 'Оттава', 33775, 565, 'ottava', 45.4213982, '2028', '2', -75.6975021, '2029', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33778, 'Лондон', 33777, 567, 'london', 51.5056, '1736', '2', -0.142770007, '1737', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33780, 'Сиде', 33779, 569, 'side', 36.7839012, '2210', '2', 31.4073009, '2211', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33782, 'Пойербах', 33781, 571, 'pojerbah', 48.3445015, '1678', '2', 13.7713003, '1679', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33784, 'Пхукет', 33783, 577, 'phuket', 7.95137978, '2192', '2', 98.357399, '2193', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33786, 'Таджикистан', 33785, 579, 'tadzhikistan', 39.0421982, '1651', '1', 70.8481979, '1654', 'COUNTRY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33788, 'Душанбе', 33787, 581, 'dushanbe', 38.5584984, '1652', '2', 68.7633972, '1653', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33790, 'Карловы Вары', 33789, 583, 'karlovy-vary', 50.2284012, '2304', '2', 12.8649998, '2305', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33792, 'Абу-Даби', 33791, 585, 'abu-dabi', 24.4382, '2118', '2', 54.4012985, '2119', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33794, 'Дубай', 33793, 587, 'dubaj', 25.2388992, '2122', '2', 55.3255997, '2123', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33796, 'Симферополь', 33795, 589, 'simferopol', 44.9520988, '1618', '2', 34.1024017, '1619', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33798, 'Грузия', 33797, 591, 'gruzija', 41.9584999, '1791', '1', 43.2607994, '1796', 'COUNTRY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33800, 'Пампорово', 33799, 595, 'pamporovo', 41.649601, '1706', '2', 24.6872005, '1707', 'CITY', '17');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33802, 'Барселона', 33801, 599, 'barselona', 41.4006004, '1854', '2', 2.14396, '1855', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33804, 'Сен-Тропе', 33803, 605, 'sen-trope', 43.2724991, '2260', '2', 6.63890982, '2261', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33806, 'Вильфранш-сюр-Мер', 33805, 607, 'vilfransh-sjur-mer', 45.9884987, '2236', '2', 4.71503019, '2237', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33808, 'Кап-д''Айи ', 33807, 609, 'kap-daji ', 43.7229004, '2244', '2', 7.39775991, '2245', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33810, 'Ла-Танья', 33809, 611, 'la-tanja', 44.6755981, '2250', '2', 3.90842009, '2251', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33812, 'Валь-д''Изер', 33811, 613, 'val-dizer', 45.4488983, '2234', '2', 6.98028994, '2235', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33814, 'Майами', 33813, 615, 'majami', 25.7747993, '2180', '2', -80.1977005, '2181', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33816, 'Аспен', 33815, 617, 'aspen', 39.1899986, '2178', '2', 106.818001, '2179', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33818, 'Марокко', 33817, 619, 'marokko', 29.1299992, '2103', '1', -8.87092018, '2106', 'COUNTRY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33820, 'Марракеш', 33819, 621, 'marrakesh', 31.6378002, '2104', '2', -8.03238964, '2105', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33822, 'Самуй', 33821, 623, 'samuj', 9.48429012, '2194', '2', 99.9957962, '2195', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33824, 'мкр Мирас', 33823, 625, 'almaty-bostandykskij-miras', 43.1889, '190', '4', 76.8766022, '191', 'MICRODISTRICT', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33826, 'Хемниц', 33825, 631, 'hemnic ', 50.8339996, '1764', '2', 12.9125004, '1765', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33828, 'Малайзия', 33827, 633, 'malajzija', 4.11650991, '2089', '1', 114.001999, '2092', 'COUNTRY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33830, 'Куала-Лумпур ', 33829, 635, 'kuala-lumpur ', 3.14828992, '2090', '2', 101.699997, '2091', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33832, 'Корея', 33831, 639, 'koreja', 38.6362991, '2085', '1', 127.874001, '2088', 'COUNTRY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33834, 'Сеул', 33833, 641, 'seul', 37.5620003, '2086', '2', 126.982002, '2087', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33836, 'Беларусь', 33835, 645, 'belorussija', 53.9019012, '1645', '1', 27.5611992, '1650', 'COUNTRY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33838, 'Минск', 33837, 647, 'minsk', 53.9061012, '1648', '2', 27.5548992, '1649', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33840, 'Берлин', 33839, 649, 'berlin', 52.9700012, '1756', '2', 13.6964998, '1757', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33842, 'Магдебург', 33841, 651, 'magdeburg', 52.1307983, '1760', '2', 11.6281004, '1761', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33844, 'Дрезден', 33843, 653, 'drezden', 51.0572014, '1758', '2', 13.7329998, '1759', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33846, 'Мюнхен', 33845, 655, 'mjunhen', 48.1604996, '1762', '2', 11.5745001, '1763', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33848, 'Штеттин', 33847, 657, 'shtettin', 53.4337997, '1766', '2', 14.5243998, '1767', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33850, 'Загреб', 33849, 659, 'zagreb', 45.8072014, '2272', '2', 15.9675999, '2273', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33852, 'Пула', 33851, 661, 'pula', 44.865799, '2274', '2', 13.8494997, '2275', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33854, 'Риека', 33853, 663, 'rieka', 45.3266983, '2276', '2', 14.4471998, '2277', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33856, 'Фетхие', 33855, 669, 'fethie', 36.6557007, '2212', '2', 29.1263008, '2213', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33858, 'Бодрум', 33857, 671, 'bodrum', 37.0344009, '2204', '2', 27.4305, '2205', 'CITY', '16');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33860, 'София', 33859, 673, 'sofija', 42.6786995, '1718', '2', 23.3398991, '1719', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33862, 'Челябинск', 33861, 691, 'cheljabinsk', 55.1603012, '1638', '2', 61.4009018, '1639', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33864, 'Княжество Андорра', 33863, 701, 'knjazhestvo andorra', 42.5091019, '2063', '1', 1.59872997, '2078', 'COUNTRY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33866, 'Андорра-Ла-Велья', 33865, 703, 'andorra-la-velya', 42.5082016, '2064', '2', 1.52769005, '2065', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33868, 'Эскальдес-Энгордань', 33867, 705, 'jeskaldjes-jengordan', 42.487999, '2076', '2', 1.58955002, '2077', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33870, 'Ла-Массана', 33869, 707, 'la-massana', 42.5429993, '2068', '2', 1.48319995, '2069', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33872, 'Энкамп', 33871, 709, 'jenkamp', 42.5237999, '2074', '2', 1.64476001, '2075', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33874, 'Канильё', 33873, 711, 'kaniljo', 42.5779991, '2066', '2', 1.67138004, '2067', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33998, 'Перуджа', 33997, 901, 'perudzha', 43.2145004, '2004', '2', 11.8003998, '2005', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33876, 'Сан-Жулиа-де-Лория', 33875, 713, 'san-zhulia-de-lorija', 42.4668999, '2072', '2', 1.49361002, '2073', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33878, 'Ордино', 33877, 715, 'ordino', 42.6062012, '2070', '2', 1.54109001, '2071', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33880, 'Лозенец', 33879, 717, 'lozenec', 43.282299, '1700', '2', 28.0832996, '1701', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33882, 'Румыния', 33881, 719, 'rumynija', 46.2991982, '2147', '1', 24.9102993, '2150', 'COUNTRY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33884, 'Бухарест', 33883, 721, 'buharest', 44.4399986, '2148', '2', 26.1039009, '2149', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33886, 'Кальп', 33885, 741, 'kalp', 38.5878983, '1878', '2', -0.257167011, '1879', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33888, 'Бениса', 33887, 743, 'benisa', 39.2882004, '1858', '2', -0.27364701, '1859', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33890, 'Морайра', 33889, 745, 'morajra', 40.1893997, '1904', '2', -0.00448196987, '1905', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33892, 'Альбир', 33891, 747, 'albir', 39.2989006, '1846', '2', -1.14706004, '1847', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33894, 'Ла Нусия', 33893, 749, 'la-nusija', 39.2220001, '1888', '2', -0.685634017, '1889', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33896, 'Финестрат', 33895, 751, 'finestrat', 39.2561989, '1950', '2', -1.26791, '1951', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33898, 'Полоп', 33897, 753, 'polop', 39.1450996, '1916', '2', -0.938319981, '1917', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33900, 'Орчета', 33899, 755, 'orcheta', 39.4098015, '1908', '2', -1.52059996, '1909', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33902, 'Льорет-де-Мар', 33901, 763, 'loret-de-mar', 41.594101, '1892', '2', 1.01724994, '1893', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33904, 'Валенсия', 33903, 765, 'valensija', 39.6310005, '1864', '2', -1.39974999, '1865', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33906, 'Нью-Йорк', 33905, 773, 'new-jork', 39.5483017, '2184', '2', -72.6691971, '2185', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33908, 'Плайя-де-Аро', 33907, 777, 'plajja-de-aro', 41.1078987, '1914', '2', -0.0803970993, '1915', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33910, 'Аргентона', 33909, 779, 'argentona', 41.6880989, '1850', '2', 2.26531005, '1851', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33912, 'мкр Тастак-1', 33911, 799, 'lmaty-aujezovskij-mkr-tastak-1', 43.2487984, '150', '4', 76.8528976, '151', 'MICRODISTRICT', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33914, 'Рас-эль-Хайма', 33913, 801, 'ras-al-hajma', 25.4251995, '2124', '2', 55.9081993, '2125', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33916, 'Шарджа', 33915, 803, 'shardzha', 25.6263008, '2128', '2', 55.696701, '2129', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33918, 'Аджман', 33917, 805, 'adzhman', 24.7173996, '2120', '2', 54.0737, '2121', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33920, 'Умм-эль-Кайвайн', 33919, 807, 'umm-jel-kajvajn', 25.5869007, '2126', '2', 55.5074005, '2127', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33922, 'Эль-Фуджайра', 33921, 809, 'jel-fudzhajra', 24.6884995, '2130', '2', 55.6963997, '2131', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33924, 'Хавея', 33923, 811, 'haveja', 38.766201, '1952', '2', -0.747879028, '1953', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33926, 'Бенитачель', 33925, 813, 'benitachel', 38.9458008, '1860', '2', -0.372267991, '1861', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33928, 'мкр Боралдай (Бурундай)', 33927, 821, 'boralday-vgorode', 43.3120003, '6', '4', 76.8110962, '7', 'MICRODISTRICT', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33930, 'мкр Первомайское', 33929, 823, 'mkr-pervomajskoe', 43.3224983, '202', '4', 76.938797, '203', 'MICRODISTRICT', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33932, 'мкр Алатау', 33931, 825, 'mkr-alatau', 43.2122993, '168', '4', 76.9036026, '169', 'MICRODISTRICT', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33934, 'мкр Ерменсай', 33933, 827, 'mkr-ermensaj', 43.1940002, '170', '4', 76.9126968, '171', 'MICRODISTRICT', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33936, 'мкр Нурлытау (Энергетик)', 33935, 829, 'mkr-nurlytau', 43.1780014, '172', '4', 76.9227982, '173', 'MICRODISTRICT', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33938, 'мкр Курамыс', 33937, 831, 'mkr-kuramys', 43.1772995, '272', '4', 76.8347015, '273', 'MICRODISTRICT', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33940, 'мкр Каргалы', 33939, 833, 'mkr-kargaly', 43.1514015, '270', '4', 76.8644028, '271', 'MICRODISTRICT', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33942, 'мкр Карагайлы', 33941, 835, 'mkr-karagajly', 43.1487999, '268', '4', 76.8551025, '269', 'MICRODISTRICT', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33944, 'мкр Жайлау', 33943, 837, 'mkr-zhajlau', 43.1646004, '260', '4', 76.8251038, '261', 'MICRODISTRICT', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33946, 'мкр Шугыла', 33945, 839, 'mkr-shugyla', 43.1943016, '282', '4', 76.7833023, '283', 'MICRODISTRICT', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33948, 'мкр Акжар', 33947, 841, 'mkr-akzhar', 43.1946983, '258', '4', 76.7913971, '259', 'MICRODISTRICT', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33950, 'мкр Таусамалы', 33949, 843, 'mkr-tausamaly', 43.1729012, '280', '4', 76.816597, '281', 'MICRODISTRICT', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33952, 'мкр Таужолы', 33951, 845, 'mkr-tauzholy', 43.1869011, '278', '4', 76.8337021, '279', 'MICRODISTRICT', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33954, 'мкр Тастыбулак', 33953, 847, 'mkr-tastybulak', 43.1749001, '276', '4', 76.8003998, '277', 'MICRODISTRICT', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33956, 'мкр Алгабас', 33955, 855, 'mkr-algabas', 43.1990013, '4', '4', 76.8348999, '5', 'MICRODISTRICT', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33958, 'мкр Теректы', 33957, 857, 'mkr-terekty', 43.3134003, '12', '4', 76.8143997, '13', 'MICRODISTRICT', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33960, 'мкр Рахат', 33959, 859, 'raxat-nizhnij', 43.2857018, '10', '4', 76.8063965, '11', 'MICRODISTRICT', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33962, 'мкр Мадениет', 33961, 861, 'mkr-madeniet', 43.2974014, '8', '4', 76.8139038, '9', 'MICRODISTRICT', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33964, 'мкр Альмерек', 33963, 863, 'mkr-almerek', 43.3569984, '286', '4', 76.9606018, '287', 'MICRODISTRICT', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33966, 'мкр Кайрат', 33965, 865, 'mkr-kajrat', 43.3554001, '298', '4', 76.9833984, '299', 'MICRODISTRICT', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33968, 'мкр Колхозши', 33967, 867, 'mkr-kolxozshi', 43.3459015, '300', '4', 76.9776001, '301', 'MICRODISTRICT', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33970, 'мкр Ремизовка', 33969, 869, 'mkr-remizovka', 43.2322006, '174', '4', 76.9023972, '175', 'MICRODISTRICT', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33972, 'мкр Актобе', 33971, 871, 'mkr-aktobe', 43.2070999, '164', '4', 76.9143982, '165', 'MICRODISTRICT', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33974, 'мкр Аккайын', 33973, 873, 'mkr-akkain', 43.1781998, '220', '4', 76.9532013, '221', 'MICRODISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33976, 'мкр Кольсай', 33975, 875, 'mkr-kolsaj', 43.2893982, '226', '4', 76.9772034, '227', 'MICRODISTRICT', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33978, 'мкр Сулусай', 33977, 877, 'mkr-sulusaj', 43.2677002, '228', '4', 76.9637985, '229', 'MICRODISTRICT', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33980, 'мкр Рахат', 33979, 879, 'mkr-raxat', 43.1766014, '274', '4', 76.8340988, '275', 'MICRODISTRICT', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33982, 'Брест', 33981, 883, 'brest', 52.5932007, '1646', '2', 25.4596004, '1647', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33984, 'мкр Калкаман-1', 33983, 887, 'kalkaman-1', 43.1668015, '262', '4', 76.816597, '263', 'MICRODISTRICT', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33986, 'мкр Калкаман-2', 33985, 889, 'kalkaman-2', 43.1708984, '264', '4', 76.8182983, '265', 'MICRODISTRICT', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33988, 'мкр Калкаман-3', 33987, 891, 'kalkaman-3', 43.1721001, '266', '4', 76.8190002, '267', 'MICRODISTRICT', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33990, 'Милан', 33989, 893, 'milan', 44.8307991, '1994', '2', 9.93542004, '1995', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33992, 'Венеция', 33991, 895, 'venecziya', 44.946701, '1978', '2', 12.1835003, '1979', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33994, 'Тоскана', 33993, 897, 'toskana', 43.7036018, '2018', '2', 11.5164003, '2019', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (33996, 'Пиза', 33995, 899, 'piza', 42.1529007, '2002', '2', 12.9173002, '2003', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34000, 'Пальма-де-Майорка', 33999, 903, 'palma-de-majorka', 39.5284996, '1912', '2', 3.16307998, '1913', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34002, 'Маон', 34001, 905, 'maon', 39.9301987, '1898', '2', 4.09779978, '1899', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34004, 'Сиудадела', 34003, 907, 'siudadela', 39.9119987, '1922', '2', 3.98377991, '1923', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34006, 'Ивиса', 34005, 909, 'ivisa', 39.050499, '1872', '2', 1.48431003, '1873', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34008, 'Марбелья', 34007, 911, 'marbelya', 36.3504982, '1900', '2', -5.13505983, '1901', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34010, 'Пуэрто-Банус', 34009, 913, 'puerto-banus', 36.4860001, '1920', '2', -4.95564985, '1921', 'CITY', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34012, 'Мурсия', 34011, 915, 'mursiya', 37.5042, '1906', '2', -2.16744995, '1907', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34014, 'Кома-Руга', 34013, 917, 'koma-ruga', 41.2071991, '1884', '2', 1.44675004, '1885', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34016, 'Сиджес', 34015, 919, 'sitzhes', 41.2475014, '1924', '2', 1.80694997, '1925', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34018, 'Кадакес', 34017, 921, 'kadakes', 42.3749008, '1874', '2', 3.20437002, '1875', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34020, 'Плайя Фламенка', 34019, 923, 'plajya-flamenka', 37.9202995, '1918', '2', -0.737882972, '1919', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34022, 'Бланес', 34021, 925, 'blanes', 41.6721001, '1862', '2', 2.82209992, '1863', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34024, 'Багур', 34023, 927, 'bagur', 41.8123016, '1852', '2', 3.09661007, '1853', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34026, 'Камбрильс', 34025, 929, 'kambrils', 41.0722008, '1876', '2', 1.04981995, '1877', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34028, 'Тосса-де-Мар', 34027, 931, 'tossa-de-mar', 41.7205009, '1948', '2', 2.92725992, '1949', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34030, 'Таррагона', 34029, 933, 'tarragona', 41.1453018, '1932', '2', 1.26095998, '1933', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34032, 'Торредембарра', 34031, 945, 'torredembarra', 41.2352982, '1946', '2', 1.64472997, '1947', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34034, 'Будва', 34033, 949, 'budva', 42.2879982, '2286', '2', 18.8400993, '2287', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34036, 'Бар', 34035, 951, 'bar', 42.1090012, '2282', '2', 19.0582008, '2283', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34038, 'Сутоморе', 34037, 953, 'sutomore', 42.1851997, '2294', '2', 18.9179001, '2295', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34040, 'Жабляк', 34039, 955, 'zhablyak', 42.7282982, '2292', '2', 19.2378006, '2293', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34042, 'Добрые воды', 34041, 957, 'dobrye-vody', 42.5536995, '2288', '2', 19.2653008, '2289', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34044, 'Утеха', 34043, 959, 'utexa', 42.5536995, '2300', '2', 19.2653008, '2301', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34046, 'Бечичи', 34045, 961, 'bechichi', 42.5171013, '2284', '2', 19.2598, '2285', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34048, 'Подгорица', 34047, 963, 'podgoricza', 42.5469017, '2290', '2', 19.0522003, '2291', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34050, 'Петровац', 34049, 965, 'petrovacz', 42.1789017, '2280', '2', 19.5345001, '2281', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34052, 'Нидерланды', 34051, 967, 'niderlandy', 51.6487007, '2107', '1', 7.36477995, '2116', 'COUNTRY', '5');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34054, 'Амстердам', 34053, 969, 'amsterdam', 51.530899, '2108', '2', 5.46083021, '2109', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34056, 'Гаага', 34055, 971, 'gaaga', 51.5663986, '2110', '2', 4.15596008, '2111', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34058, 'Утрехт', 34057, 973, 'utrext', 51.672699, '2114', '2', 5.05694008, '2115', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34060, 'Роттердам', 34059, 975, 'rotterdam', 51.9441986, '2112', '2', 4.74141979, '2113', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34062, 'Кастельдефельс', 34061, 979, 'kasteldefels', 41.3520012, '1882', '2', 1.95947003, '1883', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34064, 'Салоу', 34063, 981, 'salou', 41.0764008, '1926', '2', 1.12603998, '1927', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34066, 'Антиб', 34065, 987, 'antib', 43.6225014, '2228', '2', 7.14845991, '2229', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34068, 'Ницца', 34067, 991, 'niczcza', 43.5629005, '2254', '2', 7.31377983, '2255', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34070, 'Скалея', 34069, 993, 'skaleya', 39.6906013, '2012', '2', 15.6534996, '2013', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34072, 'Лидо-ди-Камайоре', 34071, 995, 'lido-di-kamajore', 43.9230003, '1990', '2', 9.99639034, '1991', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34074, 'Пескара', 34073, 997, 'peskara', 42.6268005, '2006', '2', 13.4186001, '2007', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34076, 'Триест', 34075, 999, 'triest', 45.5028992, '2020', '2', 13.9808998, '2021', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34078, 'Босолей', 34077, 1001, 'bosolej', 43.6825981, '2230', '2', 7.11994982, '2231', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34080, 'Ла Коль-сюр-Лу', 34079, 1003, 'la-kol-syur-lu', 43.5261002, '2246', '2', 6.32718992, '2247', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34082, 'Вильнёв-Лубе', 34081, 1005, 'vilnyov-lube', 43.7486992, '2232', '2', 6.46049976, '2233', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34084, 'Верона', 34083, 1007, 'verona', 45.4667015, '1980', '2', 10.8866997, '1981', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34086, 'Канны', 34085, 1009, 'kanny', 43.5699005, '2242', '2', 6.39909983, '2243', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34088, 'Гольф-Жуан', 34087, 1011, 'golf-zhuan', 43.2237015, '2238', '2', 6.01601982, '2239', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34090, 'Рокебрюн-Кап-Мартен', 34089, 1013, '', 43.0775986, '2256', '2', 6.19556999, '2257', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34092, 'Уфа', 34091, 1015, 'ufa', 54.7523003, '1634', '2', 56.2985001, '1635', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34094, 'Ля Турби', 34093, 1017, 'lya-turbi', 43.7722015, '2248', '2', 6.40844011, '2249', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34096, 'Бишкуль', 34095, 1019, 'bishkul', 54.7639999, '1421', '3', 68.6986008, '1422', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34098, 'Индонезия', 34097, 1021, 'indoneziya', 1.84287, '1837', '1', 115.668999, '1842', 'COUNTRY', '5');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34100, 'Денпасар', 34099, 1023, 'denpasar', -7.51966, '1838', '2', 113.945999, '1839', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34102, 'Матарам', 34101, 1025, 'mataram', -9.52742958, '1840', '2', 116.464996, '1841', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34104, 'Виареджо', 34103, 1027, 'viaredzho', 44.0228996, '1984', '2', 9.54450989, '1985', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34106, 'Фрежюс', 34105, 1029, 'frezhyus', 43.3050003, '2264', '2', 6.0869298, '2265', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34108, 'Эз', 34107, 1031, 'ez', 43.7498016, '2226', '2', 7.44868994, '2227', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34110, 'Барнаул', 34109, 1033, 'barnaul', 53.5798988, '1556', '2', 85.0115967, '1557', 'CITY', '5');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34112, 'Курган', 34111, 1035, 'kurgan', 55.8056984, '1580', '2', 66.7012024, '1581', 'CITY', '5');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34114, 'Екатеринбург', 34113, 1037, 'ekaterinburg', 54.7089996, '1570', '2', 60.9492989, '1571', 'CITY', '5');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34116, 'Томск', 34115, 1039, 'tomsk', 55.2570992, '1630', '2', 86.6074982, '1631', 'CITY', '5');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34118, 'Самара', 34117, 1041, 'samara', 51.2338982, '1610', '2', 51.4352989, '1611', 'CITY', '5');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34120, 'Волгоград', 34119, 1043, 'volgograd', 49.5948982, '1566', '2', 46.3195, '1567', 'CITY', '5');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34122, 'Магнитогорск ', 34121, 1045, 'magnitogorsk-', 53.3582001, '1586', '2', 59.2019005, '1587', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34124, 'Каратауский р-н', 34123, 1049, 'karatauskij', 42.3582001, '1462', '4', 69.5614014, '1463', 'DISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34126, 'Финляндия', 34125, 1051, 'finlyandiya', 65.5992966, '2221', '1', 31.9601994, '2222', 'COUNTRY', '4');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34128, 'Санто-Доминго', 34127, 1055, 'santo-domingo', 18.5398006, '1800', '2', -70.0274963, '1801', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34130, 'Пунта-Кана', 34129, 1057, 'punta-kanta', 18.5991993, '1798', '2', -68.8360977, '1799', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34132, 'Оренбург', 34131, 1059, 'orenburg', 51.9413986, '1592', '2', 54.4421997, '1593', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34134, 'Ларнака', 34133, 1061, 'larnaka', 35.0049019, '2038', '2', 33.0864983, '2039', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34136, 'Айя-Напа', 34135, 1063, 'ajya-napa', 35.0307999, '2032', '2', 33.8890991, '2033', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34138, 'Лачи', 34137, 1065, 'lachi', 35.1403999, '2040', '2', 33.3203011, '2041', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34140, 'Лимассол', 34139, 1067, 'limassol', 34.7126999, '2042', '2', 32.9087982, '2043', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34142, 'Никосия', 34141, 1069, 'nikosiya', 34.7974014, '2044', '2', 32.8718987, '2045', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34144, 'Пафос', 34143, 1071, 'pafos', 34.8378983, '2048', '2', 33.0810013, '2049', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34146, 'Паралимни', 34145, 1073, 'paralimni', 34.9939995, '2046', '2', 33.9030991, '2047', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34148, 'Полис', 34147, 1075, 'polis', 34.9566002, '2050', '2', 33.114399, '2051', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34150, 'Протарас', 34149, 1077, 'protaras', 35.2265015, '2052', '2', 33.1455994, '2053', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34152, 'Владимир', 34151, 1081, 'vladimir', 56.0210991, '1562', '2', 39.0758018, '1563', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34154, 'Лос-Анджелес', 34153, 1083, 'los-andzheles', 33.5559998, '2182', '2', -117.516998, '2183', 'CITY', '5');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34156, 'Сургут', 34155, 1087, 'surgut', 58.8305016, '1624', '2', 67.2232971, '1625', 'CITY', '5');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34158, 'Тиват', 34157, 1089, 'tivat', 42.5154991, '2296', '2', 19.0767994, '2297', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34160, 'Манчестер', 34159, 1091, 'manchester', 53.0726013, '1738', '2', -2.11385989, '1739', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34162, 'Ливерпуль', 34161, 1093, 'liverpul', 52.6203003, '1732', '2', -3.27840996, '1733', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34164, 'Словения', 34163, 1095, 'sloveniya', 45.7559013, '2157', '1', 15.1070995, '2176', 'COUNTRY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34166, 'Любляна', 34165, 1097, 'lyublyana', 45.8328018, '2160', '2', 15.217, '2161', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34168, 'Община Мира-Костаневица', 34167, 1099, 'obshina-mira-kostanevicza', 45.6851997, '2170', '2', 14.9575005, '2171', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34170, 'Краньска-Гора', 34169, 1101, 'kranska-gora', 45.9297981, '2158', '2', 16.3626003, '2159', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34172, 'Словень-Градец', 34171, 1103, 'sloven-gradecz', 45.8634987, '2172', '2', 15.0632, '2173', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34174, 'Ново-Место', 34173, 1105, 'novo-mesto', 45.5555992, '2168', '2', 15.6563997, '2169', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34176, 'Менгеш', 34175, 1107, 'mengesh', 46.1161995, '2166', '2', 15.0960999, '2167', 'DISTRICT', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34178, 'Лютомер', 34177, 1109, 'lyutomer', 46.3297005, '2162', '2', 15.0411997, '2163', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34180, 'Марибор', 34179, 1111, 'maribor', 46.1543999, '2164', '2', 15.1730003, '2165', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34182, 'Целе', 34181, 1113, 'czele', 46.1085014, '2174', '2', 15.0851002, '2175', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34184, 'Банско', 34183, 1115, 'bansko', 42.7221985, '1668', '2', 25.4538994, '1669', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34186, 'Кавала', 34185, 1117, 'kavala', 38.3031998, '1772', '2', 22.6905994, '1773', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34188, 'Альба-Адриатика', 34187, 1119, 'alba-adriatika', 42.6337013, '1966', '2', 13.6483002, '1967', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34190, 'Л’Акуила', 34189, 1121, 'l’akuila', 42.0070992, '1988', '2', 13.3965998, '1989', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34192, 'Aсколи-Пичено', 34191, 1123, 'askoli-picheno', 41.6394005, '1968', '2', 13.0503998, '1969', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34194, 'Вилла-Роза', 34193, 1125, 'villa-roza', 41.8265991, '1982', '2', 13.9239998, '1983', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34196, 'Торторето', 34195, 1127, 'tortoreto', 41.3978004, '2016', '2', 13.7482004, '2017', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34198, 'Нерето', 34197, 1131, 'nereto', 41.5554008, '1996', '2', 14.6401997, '1997', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34200, 'Терамо', 34199, 1133, 'teramo', 42.1599007, '2014', '2', 14.3125, '2015', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34202, 'Китай', 34201, 1137, 'kitaj', 34.9477005, '2059', '1', 97.3187027, '2062', 'COUNTRY', '4');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34204, 'Пекин', 34203, 1139, 'pekin', 35.3087006, '2060', '2', 121.379997, '2061', 'CITY', '4');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34206, 'Хургада', 34205, 1141, 'xurgada', 27.0820999, '1816', '2', 32.8204002, '1817', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34208, 'Эль-Гуна', 34207, 1143, 'el-guna', 27.9011002, '1820', '2', 31.4855003, '1821', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34210, 'Марса-эль-Алам', 34209, 1145, 'marsa-el-alam', 28.0902996, '1812', '2', 31.1504993, '1813', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34212, 'Макади', 34211, 1147, 'makadi', 26.5202007, '1810', '2', 32.9082985, '1811', 'CITY', '5');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34214, 'Елените', 34213, 1149, 'elenite', 42.7252007, '1698', '2', 27.4727993, '1699', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34216, 'Лермонтово', 34215, 1151, 'lermontovo', 44.638401, '1582', '2', 41.6214981, '1583', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34218, 'Тбилиси', 34217, 1155, 'tbilisi', 41.8025017, '1794', '2', 43.5463982, '1795', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34220, 'Батуми', 34219, 1157, 'batumi', 41.9421005, '1792', '2', 42.0192986, '1793', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34222, 'Ош', 34221, 1159, 'osh', 41.2717018, '1536', '2', 73.4501038, '1537', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34224, 'Салоники', 34223, 1161, 'saloniki', 38.8681984, '1782', '2', 23.0447998, '1783', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34226, 'Патры', 34225, 1163, 'patry', 39.1304016, '1780', '2', 22.4165993, '1781', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34228, 'Лариса', 34227, 1165, 'larisa', 38.8681984, '1778', '2', 22.2758007, '1779', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34230, 'Ламия', 34229, 1167, 'lamiya', 39.1425018, '1776', '2', 21.5286999, '1777', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34232, 'Трикеа', 34231, 1169, 'trikea', 38.8853989, '1784', '2', 22.4845009, '1785', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34234, 'Янина', 34233, 1171, 'yanina', 39.0055008, '1788', '2', 21.3484993, '1789', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34236, 'Халкида', 34235, 1173, 'xalkida', 38.6100998, '1786', '2', 23.2865009, '1787', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34238, 'Каламата', 34237, 1175, 'kalamata', 37.769001, '1774', '2', 22.8104, '1775', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34240, 'Албена', 34239, 1183, 'albena', 42.3063011, '1682', '2', 25.2826004, '1683', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34242, 'Балчик', 34241, 1185, 'balchik', 42.9311981, '1684', '2', 25.6292992, '1685', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34244, 'Пловдив', 34243, 1187, 'plovdiv', 42.6885986, '1708', '2', 25.6292992, '1709', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34246, 'Добрич', 34245, 1189, 'dobrich', 42.7857018, '1694', '2', 26.3544006, '1695', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34248, 'Воронеж', 34247, 1193, 'voronezh', 52.3027, '1564', '2', 41.8188019, '1565', 'CITY', '5');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34250, 'Сахл-Хашиш', 34249, 1195, 'saxl-xashish', 28.0116997, '1814', '2', 31.7234001, '1815', 'CITY', '5');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34252, 'Черноморец', 34251, 1197, 'chernomorecz', 42.4367981, '1722', '2', 25.0799999, '1723', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34254, 'Фамагуста', 34253, 1199, 'famagusta', 35.0834007, '2056', '2', 33.4622993, '2057', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34256, 'Искеле', 34255, 1201, 'iskele', 34.709301, '2036', '2', 32.7433014, '2037', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34258, 'Гирне', 34257, 1203, 'girne', 37.6161995, '2034', '2', 33.1817017, '2035', 'CITY', '5');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34260, 'Саратов', 34259, 1207, 'saratov', 51.5481987, '1612', '2', 46.0509987, '1613', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34262, 'Аккент', 34261, 1209, 'akkent', 43.2459984, '24', '4', 76.8149033, '25', 'MICRODISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34264, 'Каир', 34263, 1211, 'cairo', 30.0499992, '1808', '2', 31.2504997, '1809', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34266, 'Александрия', 34265, 1213, 'alexandria', 31.156601, '1804', '2', 29.8831005, '1805', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34268, 'Гиза', 34267, 1215, 'giza', 30.0039997, '1806', '2', 31.2122002, '1807', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34270, 'Сан-Ремо', 34269, 1221, 'san-remo', 43.4192009, '2010', '2', 9.67144012, '2011', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34272, 'Алассио', 34271, 1223, 'alassio', 43.5447006, '1962', '2', 10.9575996, '1963', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34274, 'Андора', 34273, 1225, 'andora', 43.331501, '1964', '2', 8.25730038, '1965', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34276, 'Берджеджи', 34275, 1227, 'berdzhedzhi', 42.7547989, '1970', '2', 11.0725002, '1971', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34278, 'Оспедалетти', 34277, 1231, 'ospedaletti', 43.2402992, '1998', '2', 12.8253002, '1999', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34280, 'Бордигера', 34279, 1233, 'bordigera', 43.2526016, '1972', '2', 12.7585001, '1973', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34282, 'Генуя', 34281, 1235, 'genuya', 43.5126991, '1974', '2', 11.8584995, '1975', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34284, 'Палермо', 34283, 1237, 'palermo', 36.7645988, '2000', '2', 15.3632002, '2001', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34286, 'Флоренция', 34285, 1239, 'florencziya', 43.2518005, '2022', '2', 10.6969004, '2023', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34288, 'Лукка', 34287, 1241, 'lukka', 43.3540993, '1992', '2', 8.96111012, '1993', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34290, 'Форте-деи-Марми', 34289, 1243, 'forte-dei-marmi', 43.2080994, '2024', '2', 12.0783005, '2025', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34292, 'Варезе', 34291, 1245, 'vareze', 43.6086006, '1976', '2', 11.1554003, '1977', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34294, 'Комо', 34293, 1247, 'komo', 43.8316994, '1986', '2', 11.7925997, '1987', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34296, 'Княжество Монако', 34295, 1249, 'knyazhestvo-monako', 42.6282997, '2079', '1', 10.5038996, '2084', 'COUNTRY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34298, 'Монако', 34297, 1251, 'monako', 42.4658012, '2080', '2', 9.91063976, '2081', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34300, 'Монте-Карло', 34299, 1253, 'monte-karlo', 42.7579994, '2082', '2', 10.1084003, '2083', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34302, 'Банско', 34301, 1255, 'balsko', 41.7999001, '1686', '2', 25.6889992, '1687', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34304, 'Баден-Баден', 34303, 1257, 'baden-baden', 49.8658981, '1754', '2', 9.28345013, '1755', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34306, 'Херцег-Нови', 34305, 1259, 'xerczeg-novi', 42.5536995, '2298', '2', 19.2653008, '2299', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34308, 'Мальта', 34307, 1261, 'malta', 35.8470993, '2093', '1', 15.4868002, '2102', 'COUNTRY', '5');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34310, 'Валетта', 34309, 1263, 'valetta', 35.8470993, '2094', '2', 15.4868002, '2095', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34312, 'Ялта', 34311, 1265, 'yaltas', 44.4515991, '1640', '2', 34.4963989, '1641', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34314, 'Евпатория', 34313, 1267, 'evpatoriya', 45.4962997, '1568', '2', 34.9994011, '1569', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34316, 'Севастополь', 34315, 1269, 'sevastopol', 43.3637009, '1620', '2', 34.4724007, '1621', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34318, 'Слима', 34317, 1271, 'slima', 35.8760986, '2100', '2', 14.4793997, '2101', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34320, 'Сент-Джулианс', 34319, 1273, 'sent-dzhulians', 35.8694992, '2098', '2', 14.4605999, '2099', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34322, 'Меллиеха', 34321, 1275, 'melliexa', 35.8927002, '2096', '2', 14.4186001, '2097', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34324, 'Струги Красные', 34323, 1285, 'strugi-krasnye', 57.8207016, '1628', '2', 28.4699993, '1629', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34326, 'Анапа', 34325, 1287, 'anapa', 44.8778992, '1552', '2', 37.3025017, '1553', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34328, 'Лугано', 34327, 1293, 'lugano', 46.9920006, '2314', '2', 8.22537994, '2315', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34330, 'Женева', 34329, 1295, 'zheneva', 46.5432014, '2312', '2', 7.14808989, '2313', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34332, 'Эвиан-ле-Бен ', 34331, 1297, 'evian-le-ben', 47.0351982, '2266', '2', 2.69814992, '2267', 'CITY', '5');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34336, 'Толлон-ле-Мемиз', 34335, 1301, 'tollon-le-memiz', 47.0351982, '2262', '2', 2.69814992, '2263', 'CITY', '5');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34338, 'Морзин', 34337, 1303, 'morzin', 47.4687996, '2252', '2', 6.19312, '2253', 'CITY', '5');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34340, 'мкр Хан Тенгри', 34339, 1307, 'mkr-han-tengri', 43.1725006, '166', '4', 76.8951035, '167', 'MICRODISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34342, 'Эстепона', 34341, 1309, 'estepona', 40.0544014, '1958', '2', -4.26717997, '1959', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34344, 'Ашдод', 34343, 1321, 'ashdod', 31.3085003, '1824', '2', 35.4263992, '1825', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34346, 'Париж', 34345, 1325, 'paris', 48.8661995, '2224', '2', 2.33724999, '2225', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34348, 'Сен-Жермен-ан-Ле', 34347, 1327, 'saint-germain-en-laye', 48.8978004, '2258', '2', 2.09267998, '2259', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34350, 'Жана Куат', 34349, 1331, 'zhana-kuat', 43.3987007, '665', '3', 77.0264969, '666', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34352, 'Орландо', 34351, 1337, 'orlando', 28.1256008, '2186', '2', -81.8239975, '2187', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34354, 'Манильва', 34353, 1339, 'manilva', 36.3633003, '1896', '2', -5.51959991, '1897', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34356, 'Феодосия', 34355, 1349, 'feodosiya', 45.1286011, '1636', '2', 34.4313011, '1637', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34358, 'Ростов-на-дону', 34357, 1351, 'rostov-na-donu', 47.236599, '1608', '2', 40.2169991, '1609', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34360, 'Кенсай', 34359, 1353, 'kensai', 43.2584, '240', '4', 77.0007019, '241', 'DISTRICT', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34362, 'Жас Канат', 34361, 1359, 'zhas-kanat', 43.3398018, '296', '4', 76.9835968, '297', 'MICRODISTRICT', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34364, 'Реутов', 34363, 1361, 'reutov', 55.6039009, '1606', '2', 37.9407005, '1607', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34366, 'Бостери', 34365, 1369, 'bosteri', 42.4664993, '1534', '2', 77.1139984, '1535', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34368, 'Марина д''Ор', 34367, 1371, 'marina-d''or', 38.8087006, '1902', '2', -3.44989991, '1903', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34370, 'Малага', 34369, 1375, 'malaga', 36.7587013, '1894', '2', -4.45124006, '1895', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34372, 'Ташкенсаз', 34371, 1379, 'tashkensaz', 43.4724998, '853', '3', 77.4040985, '854', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34374, 'Лидс', 34373, 1383, 'lids', 53.8218002, '1734', '2', -1.99425006, '1735', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34376, 'НьюКасл-апон-Тайн', 34375, 1387, 'nyukasl-apon-tajn', 54.7527008, '1740', '2', -2.11629009, '1741', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34378, 'Эльче', 34377, 1391, 'elche', 38.1943016, '1956', '2', -0.693769991, '1957', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34380, 'Туганбай', 34379, 1451, 'tuganbaj', 43.342701, '869', '3', 77.2040024, '870', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34382, 'Орск', 34381, 1469, 'orsk', 51.1679001, '1594', '2', 58.537899, '1595', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34384, 'Отемис', 34383, 1471, 'otemis', 50.9429016, '423', '3', 71.0250015, '424', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34386, 'Жалтырколь', 34385, 1475, 'zhaltyrkol', 51.0293999, '361', '3', 71.7629013, '362', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34388, 'Владивосток', 34387, 1491, 'vladivostok', 45.471199, '1560', '2', 131.542007, '1561', 'CITY', '5');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34390, 'Даугавпилс', 34389, 1527, 'daugavpils', 57.1138, '1542', '2', 24.1390991, '1543', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34392, 'Швеция', 34391, 1529, 'shvecziya', 63.0611, '2317', '1', 7.7363801, '2324', 'COUNTRY', '4');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34394, 'Осло', 34393, 1531, 'oslo', 60.955101, '2318', '2', 11.0761995, '2319', 'CITY', '4');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34396, 'Стокгольм', 34395, 1533, 'stokgolm', 60.5465012, '2320', '2', 12.8339996, '2321', 'CITY', '5');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34398, 'Эстерсунд', 34397, 1535, 'estersund', 59.9841995, '2322', '2', 17.0653992, '2323', 'CITY', '5');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34400, 'Привальное', 34399, 1557, 'privalnoe', 54.3488998, '1604', '2', 72.411499, '1605', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34402, 'Щапово', 34401, 1565, 'shapovo', 50.4051018, '1149', '3', 50.4082985, '1150', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34404, 'Коста-Бланка', 34403, 1607, 'kosta-blanka', 0, '1886', '2', 0, '1887', 'CITY', '0');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34406, 'Кызылжар', 34405, 1619, 'kyzylzhar', 51.6599998, '411', '3', 70.9891968, '412', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34408, 'Жанакурылыс', 34407, 1633, 'zhanakurylys', 44.9817009, '661', '3', 78.0864029, '662', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34410, 'Рыскулово', 34409, 1689, 'ryskulovo', 43.2627983, '833', '3', 77.2717972, '834', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34412, 'Ольгинка', 34411, 1697, 'olginka', 52.0275002, '421', '3', 70.3958969, '422', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34414, 'Куйган', 34413, 1701, 'kujgan', 43.9720993, '779', '3', 77.4150009, '780', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34416, 'Гранитный', 34415, 1711, 'granitnyj', 52.4860001, '341', '3', 72.1756973, '342', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34418, 'Сауыншы', 34417, 1731, 'sauynshy', 43.7056007, '839', '3', 76.3896027, '840', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34420, 'Назрань', 34419, 1733, 'nazran', 43.3622017, '1596', '2', 45.5858994, '1597', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34422, 'Светлогорск', 34421, 1737, 'Svetlogorsk', 54.8470993, '1616', '2', 20.2185001, '1617', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34424, 'Зеленоградск', 34423, 1739, 'Zelenogradsk', 54.8218002, '1572', '2', 20.4321003, '1573', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34426, 'Янтарный', 34425, 1741, 'Yantarniy', 54.8456993, '1642', '2', 20.0135994, '1643', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34428, 'Пионерский', 34427, 1743, 'pionerskij', 54.8909988, '1602', '2', 20.2287998, '1603', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34430, 'Липецк', 34429, 1761, 'lipeczk', 53.1511993, '1584', '2', 40.5252991, '1585', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34432, 'Теректы', 34431, 1767, 'terekty', 43.8319016, '857', '3', 76.976799, '858', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34434, 'Коксаек', 34433, 1771, 'koksaek', 43.2551003, '1491', '3', 69.3082962, '1492', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34436, 'Кумарал', 34435, 1775, 'kumaral', 43.3148994, '781', '3', 76.5233002, '782', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34438, 'Новокузнецк', 34437, 1801, 'novokuzneczk', 59.4724998, '1598', '2', 47.7276001, '1599', 'CITY', '4');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34440, 'Тонкерис', 34439, 1807, 'tonkeris1', 51.2005005, '451', '3', 71.3522034, '452', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34442, 'Ключевое', 34441, 1815, 'klyuchevoe', 50.7519989, '387', '3', 72.5437012, '388', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34444, 'Кос-Истек', 34443, 1827, 'kos-istek', 49.5411987, '495', '3', 57.3983002, '496', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34446, 'Тузусай', 34445, 1829, 'tuzusaj', 43.5138016, '873', '3', 77.1009979, '874', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34448, 'Тастак', 34447, 1835, 'tastak', 52.0381012, '447', '3', 71.3980026, '448', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34450, 'Кызылжар', 34449, 1839, 'kyzylzhar3', 45.5866013, '787', '3', 78.2401962, '788', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34452, 'Талкара', 34451, 1847, 'talkara', 51.5168991, '443', '3', 71.680397, '444', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34454, 'Отенай', 34453, 1861, 'otenaj', 45.0234985, '825', '3', 78.1846008, '826', 'CITY', '8');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34456, 'Садовое', 34455, 1865, 'sadovoe1', 0, '433', '3', 0, '434', 'CITY', '0');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34458, 'Аса', 34457, 1873, 'asa', 44.2094994, '1065', '3', 70.7882996, '1066', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34460, 'Керч', 34459, 1881, 'kerch', 45.3513985, '1576', '2', 36.4752998, '1577', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34462, 'Каракудук', 34461, 1889, 'karakuduk', 49.6343002, '493', '3', 57.289299, '494', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34464, 'Вьетнам', 34463, 1891, 'vetnam', 16.2887001, '1747', '1', 107.803001, '1752', 'COUNTRY', '5');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34466, 'Ханой', 34465, 1893, 'xanoj', 18.6713009, '1748', '2', 106.835999, '1749', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34468, 'Нячанг', 34467, 1895, 'nyachang', 14.8184996, '1750', '2', 108.710999, '1751', 'CITY', '6');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34470, 'Жалтыр', 34469, 1907, 'zhaltyr', 51.6293983, '359', '3', 69.8367004, '360', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34476, 'п. Ново-Ульбинка', 34475, 1923, 'novoulbinka', 49.9384003, '1019', '3', 82.5117035, '1020', 'CITY', '9');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34478, 'Енбекшиарал', 34477, 1929, 'enbekshiaral', 43.1694984, '535', '3', 76.3350983, '536', 'CITY', '11');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34482, 'Мирное', 34481, 1937, 'mirnoe', 50.4281006, '1193', '3', 73.2497025, '1194', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34484, 'Новый', 34483, 1939, 'noviy', 50.2882996, '507', '3', 57.0724983, '508', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34486, 'Зеленое', 34485, 1941, 'zelenoe', 51.270401, '1123', '3', 51.3763008, '1124', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34488, 'Силантьевка', 34487, 1943, 'silantevka', 53.0852013, '1293', '3', 64.251503, '1294', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34490, 'Комсомольское', 34489, 1945, 'komsomolskoe', 53.3092003, '1233', '3', 66.0701981, '1234', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34492, 'Когалы', 34491, 1947, 'kogaly', 44.3595009, '741', '3', 78.5529022, '742', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34494, 'Мирный', 34493, 1949, 'mirnyj', 49.9845009, '966', '4', 82.5639038, '967', 'DISTRICT', '15');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34496, 'Пресновка', 34495, 1951, 'presnovka', 54.5898018, '1441', '3', 67.1507034, '1442', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34498, 'Садовое ', 34497, 1953, 'sadovoe-', 51.2781982, '1141', '3', 51.4175987, '1142', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34500, 'Новая жизнь', 34499, 1955, 'novaya-zhizn', 51.0886002, '1129', '3', 51.5331993, '1130', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34502, 'Приморский', 34501, 1957, 'primorskij', 43.6227989, '1361', '3', 51.2075996, '1362', 'CITY', '14');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34504, 'Умбетали', 34503, 1959, 'umbetali', 43.2466011, '891', '3', 76.356102, '892', 'CITY', '13');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34506, 'Шет', 34505, 1961, 'shet', 47, '1153', '3', 72, '1154', 'CITY', '7');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34512, 'Степное', 34511, 1971, 'stepnoe', 49, '969', '3', 81, '970', 'CITY', '0');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34514, 'Бурыл', 34513, 1973, 'buryl', 42, '1057', '3', 71, '1058', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34524, 'Рыспай', 34523, 1989, 'ryspaj', 52, '1231', '3', 63, '1232', 'CITY', '0');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34528, 'Иртышск', 34527, 1993, 'irtyshsk', 53.3247986, '1383', '3', 75.4708023, '1384', 'CITY', '12');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34508, 'Пригородный', 34507, 1965, 'prigorodnyj_esil', 0, '308', '4', 0, '309', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34516, 'Акжар', 34515, 1975, 'akzhar', 0, '471', '3', 0, '472', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34518, 'Тюра-там', 34517, 1979, 'tyura-tam', 0, '1314', '4', 0, '1315', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34520, 'Туймебая', 34519, 1981, 'tujmebaya', 0, '590', '4', 0, '591', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34522, 'Акжар2', 34521, 1983, 'akzhar2', 0, '475', '5', 0, '476', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34526, 'Нижний Курайлы', 34525, 1991, 'nizhnij-kurajly', 0, '469', '3', 0, '470', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34474, 'Комсомольский', 34473, 1921, 'komsomolskij', 0, '314', '4', 0, '315', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34480, 'Костомар', 34479, 1931, 'kostomar', 0, '397', '3', 0, '398', 'CITY', '10');
INSERT INTO region_krisha (id, name, region_id, key, alias, latitude, leftkey, level, longitude, rightkey, type, zoom) VALUES (34510, 'Киевский', 34509, 1969, 'kievskij', 0, '1271', '3', 0, '1272', 'CITY', '10');


--
-- TOC entry 2398 (class 0 OID 0)
-- Dependencies: 182
-- Name: region_krisha_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('region_krisha_seq', 1, false);


--
-- TOC entry 2378 (class 0 OID 87107)
-- Dependencies: 188
-- Data for Name: region_olx; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (30, '56', 'Макинск', 'city', 120);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (31, '81', 'Щучинск', 'city', 123);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (32, '16', 'Степногорск', 'city', 121);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (33, '41', 'Степняк', 'city', 122);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (24, '87', 'Астана', 'city', 105);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (46, '86', 'Аягоз', 'city', 217);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (64, '101', 'Атасу', 'city', 2001);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (22, '32', 'Жем', 'city', 2002);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (103, '61', 'Шардара', 'city', 277);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (47, '18', 'Чарск', 'city', 2003);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (1, '13', 'Акмолинская обл.', 'region', 112);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (2, '2', 'Актюбинская обл.', 'region', 124);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (3, '8', 'Алматинская обл.', 'region', 132);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (4, '4', 'Атырауская обл.', 'region', 213);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (5, '14', 'Восточно-Казахстанская обл.', 'region', 216);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (6, '10', 'Жамбылская обл.', 'region', 227);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (7, '7', 'Западно-Казахстанская обл.', 'region', 232);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (8, '5', 'Карагандинская обл.', 'region', 235);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (9, '12', 'Костанайская обл.', 'region', 247);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (12, '1', 'Павлодарская обл.', 'region', 260);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (13, '11', 'Северо-Казахстанская обл.', 'region', 264);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (14, '6', 'Южно-Казахстанская обл.', 'region', 270);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (11, '9', 'Мангистауская обл.', 'region', 257);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (10, '3', 'Кызылординская обл.', 'region', 253);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (15, '26', 'Актобе', 'city', 125);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (16, '66', 'Алга', 'city', 1501);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (17, '10', 'Эмба', 'city', 131);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (18, '25', 'Хромтау', 'city', 129);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (19, '63', 'Кандыагаш', 'city', 127);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (20, '11', 'Шалкар', 'city', 130);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (21, '31', 'Темир', 'city', 128);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (23, '57', 'Акколь', 'city', 113);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (25, '34', 'Атбасар', 'city', 114);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (26, '27', 'Державинск', 'city', 116);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (27, '24', 'Кокшетау', 'city', 119);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (28, '14', 'Есиль', 'city', 118);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (29, '15', 'Ерейментау', 'city', 117);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (53, '52', 'Усть-Каменогорск', 'city', 224);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (34, '1', 'Алматы', 'city', 2);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (35, '22', 'Есик', 'city', 156);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (36, '60', 'Капчагай', 'city', 168);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (37, '20', 'Каскелен', 'city', 172);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (38, '44', 'Сарканд', 'city', 193);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (39, '40', 'Талдыкорган', 'city', 195);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (40, '59', 'Талгар', 'city', 194);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (41, '37', 'Текели', 'city', 197);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (42, '67', 'Ушарал', 'city', 202);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (43, '46', 'Уштобе', 'city', 1745);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (44, '28', 'Атырау', 'city', 214);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (45, '9', 'Кульсары', 'city', 215);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (48, '75', 'Курчатов', 'city', 220);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (49, '70', 'Риддер', 'city', 221);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (50, '58', 'Семей', 'city', 222);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (51, '78', 'Серебрянск', 'city', 223);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (52, '84', 'Шемонаиха', 'city', 226);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (54, '71', 'Зайсан', 'city', 218);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (55, '55', 'Зыряновск', 'city', 219);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (56, '17', 'Каратау', 'city', 229);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (57, '69', 'Шу', 'city', 231);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (58, '53', 'Тараз', 'city', 230);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (59, '23', 'Жанатас', 'city', 228);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (60, '68', 'Жаркент', 'city', 160);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (61, '12', 'Аксай', 'city', 1787);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (62, '19', 'Уральск', 'city', 234);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (63, '64', 'Абай', 'city', 236);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (65, '72', 'Балхаш', 'city', 237);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (66, '39', 'Караганда', 'city', 239);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (67, '43', 'Каражал', 'city', 240);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (68, '77', 'Каркаралинск', 'city', 241);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (69, '76', 'Приозерск', 'city', 242);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (70, '82', 'Сарань', 'city', 243);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (77, '33', 'Сатпаев', 'city', 244);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (78, '73', 'Шахтинск', 'city', 246);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (79, '65', 'Темиртау', 'city', 245);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (80, '38', 'Жезказган', 'city', 238);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (81, '48', 'Аркалык', 'city', 248);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (82, '51', 'Костанай', 'city', 250);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (83, '49', 'Лисаковск', 'city', 251);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (84, '79', 'Рудный', 'city', 252);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (85, '80', 'Житикара', 'city', 249);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (86, '2', 'Аральск', 'city', 254);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (87, '30', 'Байконур', 'city', 885);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (88, '35', 'Казалинск', 'city', 255);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (89, '45', 'Кызылорда', 'city', 256);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (90, '50', 'Актау', 'city', 258);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (92, '5', 'Жанаозен', 'city', 259);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (91, '62', 'Форт-Шевченко', 'city', 679);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (94, '85', 'Экибастуз', 'city', 263);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (95, '36', 'Павлодар', 'city', 262);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (97, '6', 'Мамлютка', 'city', 266);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (98, '7', 'Петропавловск', 'city', 267);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (100, '8', 'Тайынша', 'city', 269);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (101, '29', 'Кентау', 'city', 273);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (104, '47', 'Шымкент', 'city', 278);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (106, '4', 'Жетысай', 'city', 272);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (93, '83', 'Аксу', 'city', 261);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (96, '13', 'Булаево', 'city', 265);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (99, '42', 'Сергеевка', 'city', 1317);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (102, '3', 'Ленгер', 'city', 274);
INSERT INTO region_olx (id, key, name, type, regionid_id) VALUES (105, '74', 'Туркестан', 'city', 276);


--
-- TOC entry 2399 (class 0 OID 0)
-- Dependencies: 183
-- Name: region_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('region_seq', 1, false);


--
-- TOC entry 2242 (class 2606 OID 87206)
-- Name: apartment_complex_krisha_v2_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY apartment_complex_krisha
    ADD CONSTRAINT apartment_complex_krisha_v2_pkey PRIMARY KEY (id);


--
-- TOC entry 2240 (class 2606 OID 87208)
-- Name: apartment_complex_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY apartment_complex
    ADD CONSTRAINT apartment_complex_pkey PRIMARY KEY (id);


--
-- TOC entry 2222 (class 2606 OID 86882)
-- Name: crawler_groups_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY crawler_groups
    ADD CONSTRAINT crawler_groups_pkey PRIMARY KEY (id);


--
-- TOC entry 2224 (class 2606 OID 86890)
-- Name: crawler_parameters_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY crawler_parameters
    ADD CONSTRAINT crawler_parameters_pkey PRIMARY KEY (id);


--
-- TOC entry 2220 (class 2606 OID 86874)
-- Name: crawler_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY crawler
    ADD CONSTRAINT crawler_pkey PRIMARY KEY (id);


--
-- TOC entry 2226 (class 2606 OID 86898)
-- Name: crawler_proxies_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY crawler_proxies
    ADD CONSTRAINT crawler_proxies_pkey PRIMARY KEY (id);


--
-- TOC entry 2228 (class 2606 OID 86906)
-- Name: crawler_user_agents_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY crawler_user_agents
    ADD CONSTRAINT crawler_user_agents_pkey PRIMARY KEY (id);


--
-- TOC entry 2234 (class 2606 OID 87114)
-- Name: region_irr_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY region_irr
    ADD CONSTRAINT region_irr_pkey PRIMARY KEY (id);


--
-- TOC entry 2236 (class 2606 OID 87116)
-- Name: region_kn_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY region_kn
    ADD CONSTRAINT region_kn_pkey PRIMARY KEY (id);


--
-- TOC entry 2232 (class 2606 OID 87082)
-- Name: region_krisha_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY region_krisha
    ADD CONSTRAINT region_krisha_pkey PRIMARY KEY (id);


--
-- TOC entry 2238 (class 2606 OID 87118)
-- Name: region_olx_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY region_olx
    ADD CONSTRAINT region_olx_pkey PRIMARY KEY (id);


--
-- TOC entry 2230 (class 2606 OID 87084)
-- Name: region_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY region
    ADD CONSTRAINT region_pkey PRIMARY KEY (id);


--
-- TOC entry 2243 (class 2606 OID 86907)
-- Name: fk3d2003146e2b5edf; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY crawler
    ADD CONSTRAINT fk3d2003146e2b5edf FOREIGN KEY (crawlergroup_id) REFERENCES crawler_groups(id);


--
-- TOC entry 2251 (class 2606 OID 87209)
-- Name: fk4417f0b52add2120; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY apartment_complex_krisha
    ADD CONSTRAINT fk4417f0b52add2120 FOREIGN KEY (region_id) REFERENCES region(id);


--
-- TOC entry 2252 (class 2606 OID 87214)
-- Name: fk4417f0b572c02bad; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY apartment_complex_krisha
    ADD CONSTRAINT fk4417f0b572c02bad FOREIGN KEY (complex_id) REFERENCES apartment_complex(id);


--
-- TOC entry 2248 (class 2606 OID 87119)
-- Name: fk46e768ec9a34725; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY region_kn
    ADD CONSTRAINT fk46e768ec9a34725 FOREIGN KEY (region_id) REFERENCES region(id);


--
-- TOC entry 2244 (class 2606 OID 86912)
-- Name: fk59e71ff5ea06a79b; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY crawler_parameters
    ADD CONSTRAINT fk59e71ff5ea06a79b FOREIGN KEY (crawler_id) REFERENCES crawler(id);


--
-- TOC entry 2247 (class 2606 OID 87124)
-- Name: fk8960549ec9a34725; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY region_irr
    ADD CONSTRAINT fk8960549ec9a34725 FOREIGN KEY (regionid_id) REFERENCES region(id);


--
-- TOC entry 2249 (class 2606 OID 87129)
-- Name: fk89606a70c9a34725; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY region_olx
    ADD CONSTRAINT fk89606a70c9a34725 FOREIGN KEY (regionid_id) REFERENCES region(id);


--
-- TOC entry 2246 (class 2606 OID 87085)
-- Name: fka47e5535c9a34725; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY region_krisha
    ADD CONSTRAINT fka47e5535c9a34725 FOREIGN KEY (region_id) REFERENCES region(id);


--
-- TOC entry 2245 (class 2606 OID 87090)
-- Name: fkc84826f46377072f; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY region
    ADD CONSTRAINT fkc84826f46377072f FOREIGN KEY (parent_id) REFERENCES region(id);


--
-- TOC entry 2250 (class 2606 OID 87219)
-- Name: fke51a10a32add2120; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY apartment_complex
    ADD CONSTRAINT fke51a10a32add2120 FOREIGN KEY (region_id) REFERENCES region(id);


--
-- TOC entry 2389 (class 0 OID 0)
-- Dependencies: 5
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2016-05-09 04:36:11 ALMT

--
-- PostgreSQL database dump complete
--


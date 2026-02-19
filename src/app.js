const app = document.getElementById('app');

const PREFS_KEY = 'equipos_web_players_v1';
const MATCHES_KEY = 'equipos_web_matches_v1';
const AUTH_KEY = 'equipos_web_auth_v1';
const API_BASE_KEY = 'equipos_api_base_v1';

let authState = loadAuthState();
let APP_STATE = null;
let GLOBAL_EVENTS_BOUND = false;
let playersSyncTimer = null;

function normalizeApiBase(value){
  const trimmed = String(value || '').trim();
  if (!trimmed) return '';
  return trimmed.replace(/\/+$/, '');
}

function getApiBase(){
  return normalizeApiBase(localStorage.getItem(API_BASE_KEY) || '');
}

function setApiBase(url){
  const normalized = normalizeApiBase(url);
  if (!normalized) {
    localStorage.removeItem(API_BASE_KEY);
  } else {
    localStorage.setItem(API_BASE_KEY, normalized);
  }
  return normalized;
}

function loadAuthState(){
  try {
    const raw = localStorage.getItem(AUTH_KEY);
    if (!raw) return { accessToken: null, refreshToken: null, user: null };
    const data = JSON.parse(raw);
    return {
      accessToken: data?.accessToken || null,
      refreshToken: data?.refreshToken || null,
      user: data?.user || null
    };
  } catch {
    return { accessToken: null, refreshToken: null, user: null };
  }
}

function saveAuthState(state){
  authState = {
    accessToken: state?.accessToken || null,
    refreshToken: state?.refreshToken || null,
    user: state?.user || null
  };
  localStorage.setItem(AUTH_KEY, JSON.stringify(authState));
  updateAuthActionUI();
}

function clearAuthState(){
  authState = { accessToken: null, refreshToken: null, user: null };
  localStorage.removeItem(AUTH_KEY);
  updateAuthActionUI();
}

function isAuthenticated(){
  return !!authState?.accessToken;
}

function updateAuthActionUI(){
  const btn = document.getElementById('action-auth');
  if (!btn) return;
  if (isAuthenticated()) {
    const label = authState?.user?.name || authState?.user?.email || 'Cuenta';
    btn.textContent = '👤';
    btn.title = `Cuenta: ${label}`;
  } else {
    btn.textContent = '🔐';
    btn.title = 'Iniciar sesión';
  }
}

function normalizePlayer(p){
  const physical = (typeof p?.physical === 'number')
    ? p.physical
    : (typeof p?.skill === 'number' ? p.skill : 0);
  return {
    name: String(p?.name || '').trim(),
    attack: Number(p?.attack || 0),
    defense: Number(p?.defense || 0),
    physical: Number(physical || 0),
    isGoalkeeper: !!p?.isGoalkeeper
  };
}

function normalizeMatch(m){
  const timeNum = Number(m?.time || Date.now());
  return {
    id: String(m?.id ?? Date.now()),
    time: Number.isFinite(timeNum) ? timeNum : Date.now(),
    titleA: String(m?.titleA || ''),
    titleB: String(m?.titleB || ''),
    teamA: Array.isArray(m?.teamA) ? m.teamA : [],
    teamB: Array.isArray(m?.teamB) ? m.teamB : [],
    result: String(m?.result || '')
  };
}

const initialPlayers = [
  { name: 'Rulo', attack: 5.0, defense: 8.0, physical: 8.0, isGoalkeeper: true },
  { name: 'Ariel',  attack: 7.9, defense: 8.4, physical: 8.4, isGoalkeeper: false },
  { name: 'Diego',  attack: 7.3, defense: 7.4, physical: 7.3, isGoalkeeper: false },
  { name: 'Jaime',  attack: 7.2, defense: 7.5, physical: 7.6, isGoalkeeper: false },
  { name: 'Pablo V',attack: 8.0, defense: 8.0, physical: 8.0, isGoalkeeper: false },
  { name: 'Carlitos',attack: 7.0, defense: 7.5, physical: 7.5, isGoalkeeper: false },
  { name: 'Seba',   attack: 7.5, defense: 6.8, physical: 7.6, isGoalkeeper: false },
  { name: 'Feña',   attack: 6.5, defense: 7.0, physical: 6.7, isGoalkeeper: false },
  { name: 'Gustavo (P)', attack: 7.3, defense: 7.3, physical: 7.2, isGoalkeeper: false },
  { name: 'Tío Seba', attack: 6.2, defense: 7.0, physical: 6.1, isGoalkeeper: false },
  { name: 'Manuel', attack: 7.3, defense: 7.6, physical: 7.6, isGoalkeeper: false },
  { name: 'Pablo P', attack: 6.8, defense: 6.6, physical: 7.2, isGoalkeeper: false },
  { name: 'Kevin',  attack: 7.7, defense: 7.1, physical: 7.0, isGoalkeeper: false },
  { name: 'David',  attack: 7.2, defense: 6.9, physical: 7.2, isGoalkeeper: false },
  { name: 'Benja',  attack: 7.3, defense: 7.5, physical: 7.5, isGoalkeeper: false },
  { name: 'Juan',   attack: 7.1, defense: 7.4, physical: 7.2, isGoalkeeper: false },
  { name: 'Marín',  attack: 7.2, defense: 7.5, physical: 7.7, isGoalkeeper: false },
  { name: 'Felipe Ep', attack: 7.2, defense: 7.0, physical: 7.5, isGoalkeeper: false },
  { name: 'Chiqui', attack: 8.8, defense: 7.8, physical: 8.2, isGoalkeeper: false },
  { name: 'Bubu',   attack: 7.6, defense: 7.2, physical: 7.3, isGoalkeeper: false },
  { name: 'Vicho',  attack: 8.8, defense: 8.4, physical: 8.8, isGoalkeeper: false },
  { name: 'Emilio', attack: 8.8, defense: 7.6, physical: 8.5, isGoalkeeper: false },
  { name: 'Jesús',  attack: 7.3, defense: 7.3, physical: 7.3, isGoalkeeper: false },
  { name: 'Shuvert',attack: 7.3, defense: 7.5, physical: 7.7, isGoalkeeper: false },
  { name: 'Gastón', attack: 7.8, defense: 7.5, physical: 8.0, isGoalkeeper: false },
  { name: 'Richard',attack: 7.5, defense: 7.5, physical: 7.8, isGoalkeeper: false },
  { name: 'Víctor', attack: 7.4, defense: 7.4, physical: 7.4, isGoalkeeper: false },
  { name: 'Gustavo Riquelme', attack: 7.1, defense: 7.1, physical: 7.1, isGoalkeeper: false },
  { name: 'Navaloco', attack: 6.7, defense: 6.4, physical: 6.3, isGoalkeeper: false },
  { name: 'Luciano', attack: 7.3, defense: 7.1, physical: 7.0, isGoalkeeper: false },
  { name: 'Jorge', attack: 8.0, defense: 7.8, physical: 8.0, isGoalkeeper: false },
];

async function openInfoModal(){
  const t = i18n();
  let data = null;
  try {
    const res = await fetch('/info.json', { cache: 'no-cache' });
    if (res.ok) data = await res.json();
  } catch {}
  const lang = (window.__lang === 'en' ? 'en' : 'es');
  const title = data?.[lang]?.title || (lang==='en' ? 'Information' : 'Información');
  const body = data?.[lang]?.body || (lang==='en'
    ? 'Equipos Web helps you create balanced teams and save match history locally in your browser.'
    : 'Equipos Web te ayuda a crear equipos balanceados y guardar el historial de partidos localmente en tu navegador.');
  document.getElementById('info-overlay')?.remove();
  const overlay = document.createElement('div');
  overlay.id = 'info-overlay';
  overlay.style.position = 'fixed';
  overlay.style.inset = '0';
  overlay.style.background = 'rgba(0,0,0,0.5)';
  overlay.style.display = 'grid';
  overlay.style.placeItems = 'center';
  overlay.innerHTML = `
    <div class="card" style="max-width:720px; width:92vw; max-height:80vh; overflow:auto">
      <div class="row" style="justify-content:space-between">
        <h3 style="margin:0">${title}</h3>
        <button id="btn-close">${t.close}</button>
      </div>
      <div style="margin-top:8px; white-space:pre-wrap">${body}</div>
    </div>
  `;
  document.body.appendChild(overlay);
  const doClose = ()=>{ try { document.body.removeChild(overlay); } catch {} };
  overlay.addEventListener('click', (e)=>{ if (e.target===overlay) doClose(); }, { once: true });
  const btn = overlay.querySelector('#btn-close');
  btn?.addEventListener('click', doClose, { once: true });
  btn?.addEventListener('touchend', (e)=>{ try { e.preventDefault(); } catch {} doClose(); }, { passive: false, once: true });
}

function openAuthModal(){
  const t = i18n();
  document.getElementById('auth-overlay')?.remove();
  const overlay = document.createElement('div');
  overlay.id = 'auth-overlay';
  overlay.style.position = 'fixed';
  overlay.style.inset = '0';
  overlay.style.background = 'rgba(0,0,0,0.5)';
  overlay.style.display = 'grid';
  overlay.style.placeItems = 'center';

  const userLabel = authState?.user?.name || authState?.user?.email || 'Cuenta conectada';
  const logged = isAuthenticated();
  const savedBase = getApiBase();

  overlay.innerHTML = `
    <div class="card" style="max-width:560px; width:92vw; max-height:84vh; overflow:auto">
      <div class="row" style="justify-content:space-between">
        <h3 style="margin:0">Cuenta</h3>
        <button id="auth-close">${t.close}</button>
      </div>

      <div style="margin-top:10px">
        <label for="api-base" style="display:block; margin-bottom:6px">URL Backend API</label>
        <input id="api-base" type="text" placeholder="https://tu-backend.onrender.com" value="${savedBase}" style="width:100%" />
        <div class="row" style="margin-top:8px">
          <button id="save-base">Guardar URL API</button>
          <small style="opacity:0.8">Requerido para login/sync</small>
        </div>
      </div>

      ${logged ? `
        <div style="margin-top:14px">
          <p style="margin:0 0 8px"><strong>${userLabel}</strong></p>
          <div class="row">
            <button id="sync-now">Sincronizar ahora</button>
            <button id="logout" class="danger">Cerrar sesion</button>
          </div>
        </div>
      ` : `
        <div style="margin-top:14px">
          <div style="margin-top:8px">
            <input id="auth-name" type="text" placeholder="Nombre (solo registro)" style="width:100%" />
          </div>
          <div style="margin-top:8px">
            <input id="auth-email" type="text" placeholder="Email" style="width:100%" />
          </div>
          <div style="margin-top:8px">
            <input id="auth-password" type="password" placeholder="Password" style="width:100%" />
          </div>
          <div class="row" style="margin-top:10px">
            <button id="login-btn">Iniciar sesion</button>
            <button id="register-btn">Registrarse</button>
            <button id="resend-btn">Reenviar verificacion</button>
          </div>
          <small style="display:block; margin-top:8px; opacity:0.8">Si te registras, debes verificar email antes del login.</small>
        </div>
      `}
    </div>
  `;

  document.body.appendChild(overlay);
  const close = ()=>{ try { document.body.removeChild(overlay); } catch {} };
  overlay.addEventListener('click', (e)=>{ if (e.target===overlay) close(); }, { once: true });
  overlay.querySelector('#auth-close')?.addEventListener('click', close, { once: true });
  overlay.querySelector('#auth-close')?.addEventListener('touchend', (e)=>{ try { e.preventDefault(); } catch {} close(); }, { passive: false, once: true });

  const readBase = ()=>{
    const value = overlay.querySelector('#api-base')?.value || '';
    const normalized = setApiBase(value);
    updateAuthActionUI();
    return normalized;
  };

  overlay.querySelector('#save-base')?.addEventListener('click', ()=>{
    const base = readBase();
    alert(base ? `API guardada: ${base}` : 'URL API eliminada');
  });

  overlay.querySelector('#sync-now')?.addEventListener('click', async ()=>{
    const base = readBase();
    if (!base) { alert('Configura primero la URL del backend API'); return; }
    if (!APP_STATE) return;
    await hydrateFromServer(APP_STATE);
    alert('Sincronizacion completada');
  });

  overlay.querySelector('#logout')?.addEventListener('click', ()=>{
    clearAuthState();
    close();
    if (APP_STATE) render(APP_STATE);
  });

  overlay.querySelector('#login-btn')?.addEventListener('click', async ()=>{
    const base = readBase();
    if (!base) { alert('Configura primero la URL del backend API'); return; }
    const email = String(overlay.querySelector('#auth-email')?.value || '').trim();
    const password = String(overlay.querySelector('#auth-password')?.value || '');
    if (!email || !password) { alert('Email y password son obligatorios'); return; }
    try {
      const data = await apiRequest('/auth/login', { method: 'POST', body: { email, password } });
      if (!data?.accessToken) throw new Error('login_failed');
      saveAuthState({
        accessToken: data.accessToken,
        refreshToken: data.refreshToken,
        user: data.user || { email }
      });
      if (APP_STATE) await hydrateFromServer(APP_STATE);
      close();
    } catch (err) {
      const msg = String(err?.message || 'error');
      if (msg.includes('email_not_verified')) alert('Debes verificar tu email antes de iniciar sesion');
      else alert(`Login fallido: ${msg}`);
    }
  });

  overlay.querySelector('#register-btn')?.addEventListener('click', async ()=>{
    const base = readBase();
    if (!base) { alert('Configura primero la URL del backend API'); return; }
    const name = String(overlay.querySelector('#auth-name')?.value || '').trim();
    const email = String(overlay.querySelector('#auth-email')?.value || '').trim();
    const password = String(overlay.querySelector('#auth-password')?.value || '');
    if (!name || !email || password.length < 6) {
      alert('Nombre, email y password (min 6) son obligatorios');
      return;
    }
    try {
      await apiRequest('/auth/register', { method: 'POST', body: { name, email, password } });
      alert('Registro exitoso. Revisa tu email para verificar la cuenta.');
    } catch (err) {
      alert(`Registro fallido: ${String(err?.message || 'error')}`);
    }
  });

  overlay.querySelector('#resend-btn')?.addEventListener('click', async ()=>{
    const base = readBase();
    if (!base) { alert('Configura primero la URL del backend API'); return; }
    const email = String(overlay.querySelector('#auth-email')?.value || '').trim();
    if (!email) { alert('Ingresa tu email'); return; }
    try {
      await apiRequest('/auth/send-verification', { method: 'POST', body: { email } });
      alert('Si la cuenta existe, se envio un nuevo email de verificacion');
    } catch (err) {
      alert(`No se pudo reenviar: ${String(err?.message || 'error')}`);
    }
  });
}
const weights = { attack: 0.35, defense: 0.35, physical: 0.30 };
function rating(p){ return p.attack*weights.attack + p.defense*weights.defense + p.physical*weights.physical; }

function loadPlayers(){
  try {
    const raw = localStorage.getItem(PREFS_KEY);
    const fallback = initialPlayers.slice();
    const arr = raw ? JSON.parse(raw) : fallback;
    const list = Array.isArray(arr) ? arr : fallback;
    return list.map(normalizePlayer).filter(p => p.name);
  } catch {
    return initialPlayers.slice().map(normalizePlayer).filter(p => p.name);
  }
}

function savePlayers(players, opts = {}){
  const normalized = (players || []).map(normalizePlayer).filter(p => p.name);
  localStorage.setItem(PREFS_KEY, JSON.stringify(normalized));
  if (!opts.skipRemote && isAuthenticated()) queuePlayersSync(normalized);
}

function loadMatches(){
  try {
    const raw = localStorage.getItem(MATCHES_KEY);
    if (!raw) return [];
    const arr = JSON.parse(raw);
    return Array.isArray(arr) ? arr.map(normalizeMatch) : [];
  } catch { return []; }
}

function saveMatches(list){
  const normalized = (list || []).map(normalizeMatch);
  localStorage.setItem(MATCHES_KEY, JSON.stringify(normalized));
}

function addMatch(titleA, titleB, teamA, teamB){
  const current = loadMatches();
  const now = Date.now();
  const tempId = `local-${now}`;
  const m = { id: tempId, time: now, titleA, titleB, teamA, teamB, result: '' };
  current.unshift(m);
  saveMatches(current);
  if (isAuthenticated()) {
    (async ()=>{
      try {
        const created = await apiRequest('/matches', {
          method: 'POST',
          auth: true,
          body: { time: now, titleA, titleB, teamA, teamB, result: '' }
        });
        if (!created?.id) return;
        const updated = loadMatches().map(item => item.id === tempId ? normalizeMatch(created) : item);
        saveMatches(updated);
      } catch (err) {
        console.error('addMatch sync error', err);
      }
    })();
  }
}

function deleteMatch(id){
  const idStr = String(id);
  const current = loadMatches();
  const updated = current.filter(m=>String(m.id)!==idStr);
  saveMatches(updated);
  if (isAuthenticated() && !idStr.startsWith('local-')) {
    apiRequest(`/matches/${encodeURIComponent(idStr)}`, { method: 'DELETE', auth: true }).catch(err => {
      console.error('deleteMatch sync error', err);
    });
  }
}

function clearAllMatches(){
  const current = loadMatches();
  saveMatches([]);
  if (isAuthenticated()) {
    current.forEach(m => {
      const id = String(m.id || '');
      if (!id || id.startsWith('local-')) return;
      apiRequest(`/matches/${encodeURIComponent(id)}`, { method: 'DELETE', auth: true }).catch(() => {});
    });
  }
}

function updateMatchResult(id, result){
  const idStr = String(id);
  const updated = loadMatches().map(m=> String(m.id)===idStr ? ({...m, result}) : m);
  saveMatches(updated);
  if (isAuthenticated() && !idStr.startsWith('local-')) {
    apiRequest(`/matches/${encodeURIComponent(idStr)}`, {
      method: 'PUT',
      auth: true,
      body: { result }
    }).catch(err => {
      console.error('updateMatchResult sync error', err);
    });
  }
}

function queuePlayersSync(players){
  clearTimeout(playersSyncTimer);
  playersSyncTimer = setTimeout(() => {
    syncPlayersToServer(players).catch(err => console.error('players bulk sync error', err));
  }, 400);
}

async function refreshAccessToken(){
  if (!authState?.refreshToken) return false;
  const base = getApiBase();
  if (!base) return false;
  try {
    const res = await fetch(`${base}/auth/refresh`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ refreshToken: authState.refreshToken })
    });
    if (!res.ok) return false;
    const data = await res.json();
    if (!data?.accessToken) return false;
    saveAuthState({ ...authState, accessToken: data.accessToken, refreshToken: data.refreshToken || authState.refreshToken });
    return true;
  } catch {
    return false;
  }
}

async function apiRequest(path, options = {}){
  const {
    method = 'GET',
    body = undefined,
    auth = false,
    retry = true
  } = options;

  const base = getApiBase();
  if (!base) throw new Error('api_base_not_configured');
  const endpoint = `${base}${path}`;
  const headers = { 'Content-Type': 'application/json' };

  if (auth && authState?.accessToken) {
    headers.Authorization = `Bearer ${authState.accessToken}`;
  }

  const response = await fetch(endpoint, {
    method,
    headers,
    body: body === undefined ? undefined : JSON.stringify(body)
  });

  if (response.status === 401 && auth && retry) {
    const refreshed = await refreshAccessToken();
    if (refreshed) {
      return apiRequest(path, { ...options, retry: false });
    }
    clearAuthState();
    throw new Error('unauthorized');
  }

  const text = await response.text();
  let payload = null;
  try { payload = text ? JSON.parse(text) : null; } catch { payload = text; }

  if (!response.ok) {
    const msg = (payload && typeof payload === 'object' && payload.error) ? payload.error : `${response.status}`;
    throw new Error(String(msg));
  }
  return payload;
}

async function syncPlayersToServer(players){
  if (!isAuthenticated()) return;
  const list = await apiRequest('/players/bulk', {
    method: 'POST',
    auth: true,
    body: { players: players.map(normalizePlayer) }
  });
  if (!Array.isArray(list)) return;
  const normalized = list.map(normalizePlayer).filter(p => p.name);
  savePlayers(normalized, { skipRemote: true });
  if (APP_STATE) {
    APP_STATE.players = normalized;
    APP_STATE.selected = new Set(normalized.map(p => p.name));
    APP_STATE.count = Math.min(Math.max(APP_STATE.count, 2), Math.max(2, normalized.length));
    render(APP_STATE);
  }
}

async function hydrateFromServer(state){
  if (!isAuthenticated()) return;
  if (!getApiBase()) return;
  try {
    const [remotePlayers, remoteMatches] = await Promise.all([
      apiRequest('/players', { auth: true }),
      apiRequest('/matches', { auth: true })
    ]);

    if (Array.isArray(remotePlayers) && remotePlayers.length > 0) {
      const players = remotePlayers.map(normalizePlayer).filter(p => p.name);
      savePlayers(players, { skipRemote: true });
      state.players = players;
      state.selected = new Set(players.map(p => p.name));
      state.count = Math.min(Math.max(state.count, 2), Math.max(2, players.length));
    } else if (Array.isArray(remotePlayers) && remotePlayers.length === 0) {
      const localPlayers = loadPlayers();
      if (localPlayers.length) {
        await syncPlayersToServer(localPlayers);
      }
    }

    if (Array.isArray(remoteMatches)) {
      const matches = remoteMatches.map(normalizeMatch).sort((a, b) => b.time - a.time);
      saveMatches(matches);
    }

    render(state);
  } catch (err) {
    console.error('hydrateFromServer error', err);
  }
}

function formatTeamBlock(title, team){
  const avg = team.length ? (team.reduce((s,p)=>s+rating(p),0)/team.length).toFixed(2) : '0.00';
  const header = `${title} (${team.length}) - Promedio: ${avg}`;
  const body = team.map(p=>`• ${p.isGoalkeeper? '(GK) ':''}${p.name}`).join('\n');
  return `${header}\n${body}`;
}

function formatTeamsText(titleA, titleB, teamA, teamB){
  const a = formatTeamBlock(titleA, teamA);
  const b = formatTeamBlock(titleB, teamB);
  return `${a}\n\n${b}`;
}

function formatSavedMatchText(m){
  const a = formatTeamBlock(m.titleA, m.teamA);
  const b = formatTeamBlock(m.titleB, m.teamB);
  const res = m.result && m.result.trim() ? `\n\nResultado: ${m.result.trim()}` : '';
  return `${a}\n\n${b}${res}`;
}

function generateBalancedTeams(chosen){
  if (!chosen || !chosen.length) return [[], []];
  const byRating = (a,b)=> (rating(b)-rating(a));
  const goalkeepers = chosen.filter(p=>p.isGoalkeeper).slice().sort(()=>Math.random()-0.5);
  const rest = chosen.filter(p=>!p.isGoalkeeper).slice().sort((a,b)=> (rating(b)-rating(a)) || (Math.random()-0.5));
  const teamA = [], teamB = [];
  let aA=0, dA=0, sA=0, rA=0, aB=0, dB=0, sB=0, rB=0;
  function objectiveAfter(addToA, p){
    const naA = addToA ? aA + p.attack : aA;
    const ndA = addToA ? dA + p.defense : dA;
    const nsA = addToA ? sA + p.physical : sA;
    const nrA = addToA ? rA + rating(p) : rA;
    const naB = addToA ? aB : aB + p.attack;
    const ndB = addToA ? dB : dB + p.defense;
    const nsB = addToA ? sB : sB + p.physical;
    const nrB = addToA ? rB : rB + rating(p);
    const da = naA - naB, dd = ndA - ndB, ds = nsA - nsB, dr = nrA - nrB;
    return da*da + dd*dd + ds*ds + dr*dr;
  }
  if (goalkeepers.length >= 2){
    const gkA = goalkeepers[0], gkB = goalkeepers[1];
    teamA.push(gkA); aA+=gkA.attack; dA+=gkA.defense; sA+=gkA.physical; rA+=rating(gkA);
    teamB.push(gkB); aB+=gkB.attack; dB+=gkB.defense; sB+=gkB.physical; rB+=rating(gkB);
  }
  const pool = (goalkeepers.length>=2? rest : goalkeepers.concat(rest));
  for (const p of pool){
    const toA = objectiveAfter(true, p);
    const toB = objectiveAfter(false, p);
    if (toA < toB){ teamA.push(p); aA+=p.attack; dA+=p.defense; sA+=p.physical; rA+=rating(p); }
    else if (toB < toA){ teamB.push(p); aB+=p.attack; dB+=p.defense; sB+=p.physical; rB+=rating(p); }
    else { // tie: random side to break determinism
      if (Math.random() < 0.5){ teamA.push(p); aA+=p.attack; dA+=p.defense; sA+=p.physical; rA+=rating(p); }
      else { teamB.push(p); aB+=p.attack; dB+=p.defense; sB+=p.physical; rB+=rating(p); }
    }
  }
  while (Math.abs(teamA.length - teamB.length) > 1){
    if (teamA.length > teamB.length){
      const moved = teamA.pop();
      teamB.push(moved); aA-=moved.attack; dA-=moved.defense; sA-=moved.physical; rA-=rating(moved); aB+=moved.attack; dB+=moved.defense; sB+=moved.physical; rB+=rating(moved);
    } else {
      const moved = teamB.pop();
      teamA.push(moved); aB-=moved.attack; dB-=moved.defense; sB-=moved.physical; rB-=rating(moved); aA+=moved.attack; dA+=moved.defense; sA+=moved.physical; rA+=rating(moved);
    }
  }
  return [teamA, teamB];
}

function teamCost(aA,dA,sA,rA,aB,dB,sB,rB){
  const da=aA-aB, dd=dA-dB, ds=sA-sB, dr=rA-rB; return da*da+dd*dd+ds*ds+dr*dr;
}

function evaluateTeams(a,b){
  const sums = (t)=>t.reduce((acc,p)=>{acc[0]+=p.attack; acc[1]+=p.defense; acc[2]+=p.physical; acc[3]+=rating(p); return acc;}, [0,0,0,0]);
  const [aA,dA,sA,rA]=sums(a); const [aB,dB,sB,rB]=sums(b);
  return teamCost(aA,dA,sA,rA,aB,dB,sB,rB);
}

function teamsSignature(a,b){
  const sa = a.map(p=>p.name).sort().join('|');
  const sb = b.map(p=>p.name).sort().join('|');
  return sa < sb ? `${sa}__${sb}` : `${sb}__${sa}`;
}

function generateBalancedTeamsStochastic(chosen, attempts=7){
  let best = null; let bestCost = Infinity; const results = [];
  for (let i=0;i<attempts;i++){
    // jitter by shuffling the chosen set slightly each attempt
    const perm = chosen.slice().sort(()=>Math.random()-0.5);
    let [a,b] = generateBalancedTeams(perm);
    // local improvement: try one beneficial swap randomly a few times
    for (let k=0;k<3;k++){
      if (!a.length || !b.length) break;
      const ai = Math.floor(Math.random()*a.length);
      const bi = Math.floor(Math.random()*b.length);
      const a1 = a.slice(), b1 = b.slice();
      const pa = a1[ai], pb = b1[bi];
      a1[ai] = pb; b1[bi] = pa;
      if (evaluateTeams(a1,b1) <= evaluateTeams(a,b)) { a = a1; b = b1; }
    }
    const cost = evaluateTeams(a,b);
    results.push({a,b,cost});
    if (cost < bestCost){ bestCost = cost; best = {a,b}; }
  }
  // pick randomly among top 3 results to increase diversity
  results.sort((x,y)=>x.cost - y.cost);
  const pick = results.slice(0, Math.min(3, results.length));
  const r = pick[Math.floor(Math.random()*pick.length)] || best;
  return [r.a, r.b];
}

function avg(list){ if(!list.length) return 0; return list.reduce((s,p)=>s+rating(p),0)/list.length; }

function i18n(){
  const en = {
    selectPlayersAndCount: 'Select players and count',
    enabledCount: (n)=>`Enabled: ${n}`,
    edit: 'Edit',
    selectAll: 'Select all',
    playersCount: (n)=>`Players count: ${n}`,
    generate: 'Generate',
    create: 'Create',
    undo: 'Undo',
    playersTapToSelect: 'Players (tap to select)',
    teamA: 'Team A',
    teamB: 'Team B',
    avg: (v)=>`Average: ${v.toFixed(2)}`,
    hideResults: 'Hide results',
    showResults: 'Show results',
    share: 'Share',
    saveMatch: 'Save match',
    saveChanges: 'Save changes',
    history: 'History',
    close: 'Close',
    clearHistory: 'Clear history',
    delete: 'Delete',
    result: 'Result',
    syncOnline: 'Cloud sync enabled',
    syncOffline: 'Local mode only'
  };
  const es = {
    selectPlayersAndCount: 'Selecciona jugadores y cantidad',
    enabledCount: (n)=>`Habilitados: ${n}`,
    edit: 'Editar',
    selectAll: 'Seleccionar todo',
    playersCount: (n)=>`Cantidad de jugadores: ${n}`,
    generate: 'Generar',
    create: 'Crear',
    undo: 'Deshacer',
    playersTapToSelect: 'Jugadores (toca para seleccionar)',
    teamA: 'Equipo A',
    teamB: 'Equipo B',
    avg: (v)=>`Promedio: ${v.toFixed(2)}`,
    hideResults: 'Ocultar resultados',
    showResults: 'Mostrar resultados',
    share: 'Compartir',
    saveMatch: 'Guardar partido',
    saveChanges: 'Aplicar Cambios',
    history: 'Historial',
    close: 'Cerrar',
    clearHistory: 'Limpiar historial',
    delete: 'Eliminar',
    result: 'Resultado',
    syncOnline: 'Sincronizacion en la nube activa',
    syncOffline: 'Solo modo local'
  };
  // add labels for scoring fields
  en.attack = 'Attack'; en.defense = 'Defense'; en.physical = 'Physical';
  es.attack = 'Ataque'; es.defense = 'Defensa'; es.physical = 'Físico';
  return window.__lang === 'en' ? en : es;
}

function render(appState){
  const t = i18n();
  const prevScroller = document.getElementById('players-scroll');
  const prevScrollTop = prevScroller ? prevScroller.scrollTop : 0;
  const selectedPlayers = appState.players.filter(p=>appState.selected.has(p.name));
  const allSelected = appState.players.length>0 && appState.selected.size === appState.players.length;
  const titleA = appState.teamATitle || t.teamA;
  const titleB = appState.teamBTitle || t.teamB;
  const icon = (p)=> p.isGoalkeeper ? '🧤' : '👕';

  app.innerHTML = `
    <div class="card">
      <p style="margin:0 0 8px; font-weight:600">${t.selectPlayersAndCount}</p>
      <div class="row" style="justify-content:space-between">
        <span>${t.enabledCount(selectedPlayers.length)}</span>
        
      </div>
      <div style="margin-top:4px">
        <small style="opacity:0.8">${isAuthenticated() ? t.syncOnline : t.syncOffline}</small>
      </div>
      <div class="row" style="margin-top:6px">
        <input type="checkbox" id="chk-all" ${allSelected?'checked':''} />
        <label for="chk-all">${t.selectAll}</label>
      </div>
      <div style="margin-top:8px">
        <div id="players-count-label">${t.playersCount(appState.count)}</div>
        <input id="slider" type="range" min="2" max="22" step="1" value="${appState.count}" style="width:100%; height:32px" />
      </div>

      <div class="row" style="margin-top:12px; gap:8px">
        <button id="btn-generate" class="btn-lg" style="flex:1">${t.generate}</button>
        <button id="btn-create" class="btn-lg" style="flex:1">${t.create}</button>
        <button id="btn-undo" class="btn-lg" style="flex:0.8">${t.undo}</button>
      </div>

      <div style="margin-top:12px; border-top:1px solid var(--divider);"></div>
      <div style="margin-top:12px">
        <div id="players-scroll" style="max-height:420px; overflow:auto">
          <ul id="players">
            ${appState.players.slice().sort((a,b)=> a.name.localeCompare(b.name, undefined, { sensitivity:'base' })).map(p=>{
              const checked = appState.selected.has(p.name)?'checked':'';
              const gk = p.isGoalkeeper? ' (GK)' : '';
              return `<li class="row" style="justify-content:space-between; padding:8px 0">
                <label class="row" style="gap:6px"><input type="checkbox" data-name="${p.name}" ${checked} /> <span>${icon(p)}</span> ${p.name}${gk}</label>
                
              </li>`
            }).join('')}
          </ul>
        </div>
      </div>

      ${appState.teamA.length && appState.teamB.length ? `
      <div class="row" style="margin-top:12px; justify-content:space-between">
        <button id="btn-toggle">${appState.showResults ? t.hideResults : t.showResults}</button>
        <div class="row" style="gap:8px">
          <button id="btn-share">${t.share}</button>
          <button id="btn-save">${t.saveMatch}</button>
        </div>
      </div>
      ` : ''}
    </div>
  `;

  document.getElementById('chk-all').addEventListener('change', (e)=>{
    if (e.target.checked){
      appState.selected = new Set(appState.players.map(p=>p.name));
    } else {
      appState.selected = new Set();
    }
    render(appState);
  });

  const sliderEl = document.getElementById('slider');
  sliderEl.addEventListener('input', (e)=>{
    const v = Math.max(2, Math.min(22, parseInt(e.target.value||'10',10)));
    appState.count = v;
    const label = document.getElementById('players-count-label');
    if (label) label.textContent = t.playersCount(appState.count);
  });
  sliderEl.addEventListener('change', ()=>{ render(appState); });

  document.getElementById('players').querySelectorAll('input[type="checkbox"]').forEach(cb=>{
    cb.addEventListener('change', ()=>{
      const name = cb.getAttribute('data-name');
      if (cb.checked) appState.selected.add(name); else appState.selected.delete(name);
      render(appState);
    });
  });

  document.getElementById('btn-undo').addEventListener('click', ()=>{
    appState.teamA = [];
    appState.teamB = [];
    render(appState);
  });

  document.getElementById('btn-toggle')?.addEventListener('click', ()=>{
    appState.showResults = !appState.showResults;
    render(appState);
  });

  // restore scroll position of players list after render
  const newScroller = document.getElementById('players-scroll');
  if (newScroller && typeof prevScrollTop === 'number') newScroller.scrollTop = prevScrollTop;

  document.getElementById('btn-share')?.addEventListener('click', async ()=>{
    const text = formatTeamsText(titleA, titleB, appState.teamA, appState.teamB);
    try {
      if (navigator.share){ await navigator.share({ text }); }
      else {
        await navigator.clipboard.writeText(text);
        alert('Copiado al portapapeles');
      }
    } catch {}
  });

  document.getElementById('btn-save')?.addEventListener('click', ()=>{
    addMatch(titleA, titleB, appState.teamA, appState.teamB);
    alert('Partido guardado');
  });

  // Render bottom drawer for teams visualization
  const existingDrawer = document.getElementById('results-drawer');
  if (existingDrawer) existingDrawer.remove();
  if (appState.teamA.length && appState.teamB.length && appState.showResults){
    const drawer = document.createElement('div');
    drawer.id = 'results-drawer';
    drawer.style.position = 'fixed';
    drawer.style.left = '0';
    drawer.style.right = '0';
    drawer.style.bottom = '0';
    drawer.style.background = 'var(--card, #fff)';
    drawer.style.borderTop = '1px solid var(--divider)';
    drawer.style.boxShadow = '0 -8px 24px rgba(0,0,0,0.08)';
    drawer.style.padding = '12px';
    drawer.style.maxHeight = '48vh';
    drawer.style.overflow = 'auto';
    drawer.innerHTML = `
      <div class="row" style="justify-content:space-between; align-items:center">
        <strong>Resultados</strong>
        <button id="close-results">${t.hideResults}</button>
      </div>
      <div class="row" style="gap:12px; align-items:flex-start; margin-top:8px">
        <div style="flex:1" class="card">
          <h3 style="margin:0 0 6px" class="row" style="align-items:center; gap:8px">
            <span style="flex:1">${titleA}</span>
            <button id="rename-a" title="${t.edit}">✎</button>
          </h3>
          
          <ul style="margin-top:8px">
            ${(appState.teamA.slice().sort(()=>Math.random()-0.5)).map(p=>`<li>${icon(p)} ${p.name}${p.isGoalkeeper? ' (GK)':''}</li>`).join('')}
          </ul>
        </div>
        <div style="flex:1" class="card">
          <h3 style="margin:0 0 6px" class="row" style="align-items:center; gap:8px">
            <span style="flex:1">${titleB}</span>
            <button id="rename-b" title="${t.edit}">✎</button>
          </h3>
          
          <ul style="margin-top:8px">
            ${(appState.teamB.slice().sort(()=>Math.random()-0.5)).map(p=>`<li>${icon(p)} ${p.name}${p.isGoalkeeper? ' (GK)':''}</li>`).join('')}
          </ul>
        </div>
      </div>
    `;
    document.body.appendChild(drawer);
    drawer.querySelector('#close-results')?.addEventListener('click', ()=>{ appState.showResults = false; render(appState); });
    // Rebind rename handlers for elements inside drawer
    document.getElementById('rename-a')?.addEventListener('click', ()=>{
      const v = prompt('Nombre del equipo A', titleA) || '';
      const t2 = v.trim();
      if (t2){ appState.teamATitle = t2; render(appState); }
    });
    document.getElementById('rename-b')?.addEventListener('click', ()=>{
      const v = prompt('Nombre del equipo B', titleB) || '';
      const t2 = v.trim();
      if (t2){ appState.teamBTitle = t2; render(appState); }
    });
  }

  document.getElementById('btn-generate').addEventListener('click', ()=>{
    const n = appState.count;
    const selected = appState.players.filter(p=>appState.selected.has(p.name));
    if (n < 2) { alert('El mínimo es 2'); return; }
    if (n > selected.length) { alert('No hay suficientes jugadores seleccionados'); return; }
    const gks = selected.filter(p=>p.isGoalkeeper).slice().sort(()=>Math.random()-0.5);
    const mustInclude = gks.length >= 2 && n >= 2 ? gks.slice(0,2) : gks.slice(0, Math.min(gks.length, n));
    const restCount = n - mustInclude.length;
    const restPool = selected.filter(p=>!p.isGoalkeeper);
    const chosen = mustInclude.concat(restPool.sort(()=>Math.random()-0.5).slice(0, restCount)).sort(()=>Math.random()-0.5);
    let tries = 0;
    const prevSig = appState.lastSig || '';
    let a, b, sig = '';
    do {
      [a,b] = generateBalancedTeamsStochastic(chosen, 9);
      if (Math.random() < 0.5) { const tmp = a; a = b; b = tmp; }
      sig = teamsSignature(a,b);
      tries++;
    } while (sig === prevSig && tries < 12);
    appState.teamA = a; appState.teamB = b; appState.showResults = true; appState.lastSig = sig;
    render(appState);
  });

  document.getElementById('btn-create').addEventListener('click', ()=>{
    openCustomizeDialog();
  });
  document.getElementById('btn-edit')?.addEventListener('click', ()=> openEditPlayersDialog());
  document.getElementById('rename-a')?.addEventListener('click', ()=>{
    const v = prompt('Nombre del equipo A', titleA) || '';
    const t2 = v.trim();
    if (t2){ appState.teamATitle = t2; render(appState); }
  });
  document.getElementById('rename-b')?.addEventListener('click', ()=>{
    const v = prompt('Nombre del equipo B', titleB) || '';
    const t2 = v.trim();
    if (t2){ appState.teamBTitle = t2; render(appState); }
  });
}

function init(){
  const players = loadPlayers();
  const state = {
    players,
    selected: new Set(players.map(p=>p.name)),
    count: Math.min(10, Math.max(2, players.length)),
    teamA: [],
    teamB: [],
    showResults: true,
    lastSig: ''
  };
  APP_STATE = state;
  render(state);
  updateAuthActionUI();

  const infoBtn = document.getElementById('action-info');
  infoBtn?.addEventListener('click', ()=> openInfoModal());

  const historyBtn = document.getElementById('action-history');
  historyBtn?.addEventListener('click', ()=> openHistoryModal());

  const authBtn = document.getElementById('action-auth');
  authBtn?.addEventListener('click', ()=> openAuthModal());

  const langBtn = document.getElementById('action-language');
  langBtn?.addEventListener('click', ()=>{
    window.__lang = window.__lang === 'en' ? 'es' : 'en';
    render(state);
  });

  if (!GLOBAL_EVENTS_BOUND) {
    GLOBAL_EVENTS_BOUND = true;
    let lastUpTime = -1, lastDownTime = -1;
    let lastVolUp = -1, lastVolDown = -1;
    window.addEventListener('keydown', (e)=>{
      const now = Date.now();
      const key = e.key || e.code;
      if (key === 'ArrowUp'){
        lastUpTime = now;
        if (lastDownTime > 0 && (now - lastDownTime) <= 300) {
          openEditPlayersDialog();
        }
      } else if (key === 'ArrowDown'){
        lastDownTime = now;
        if (lastUpTime > 0 && (now - lastUpTime) <= 300) {
          openEditPlayersDialog();
        }
      } else if (key === 'AudioVolumeUp' || key === 'VolumeUp'){
        lastVolUp = now;
        if (lastVolDown > 0 && (now - lastVolDown) <= 400) {
          try { e.preventDefault(); } catch {}
          openEditPlayersDialog();
        }
      } else if (key === 'AudioVolumeDown' || key === 'VolumeDown'){
        lastVolDown = now;
        if (lastVolUp > 0 && (now - lastVolUp) <= 400) {
          try { e.preventDefault(); } catch {}
          openEditPlayersDialog();
        }
      }
    }, { passive: false });

    // triple tap on non-interactive empty areas to open Edit dialog
    let tapTimes = [];
    let lastTapPos = null;
    const isInteractive = (el)=> !!el.closest('button, input, select, textarea, label, a, [role="button"], [data-touch-exclude]');
    window.addEventListener('touchend', (e)=>{
      try {
        if (!e || !e.target || isInteractive(e.target)) return;
        const t = e.changedTouches && e.changedTouches[0];
        if (!t) return;
        const now = Date.now();
        const pos = { x: t.clientX, y: t.clientY };
        tapTimes = tapTimes.filter(ts=> now - ts <= 600);
        if (lastTapPos){
          const dx = pos.x - lastTapPos.x, dy = pos.y - lastTapPos.y;
          const dist2 = dx*dx + dy*dy;
          if (dist2 > 1600) {
            tapTimes = [];
          }
        }
        tapTimes.push(now);
        lastTapPos = pos;
        if (tapTimes.length >= 3){
          tapTimes = [];
          lastTapPos = null;
          openEditPlayersDialog();
        }
      } catch {}
    }, { passive: true });
  }

  if (isAuthenticated()) {
    hydrateFromServer(state);
  }
}

init();

const year = document.getElementById('year');
if (year) year.textContent = new Date().getFullYear();

function openHistoryModal(){
  const t = i18n();
  const matches = loadMatches();
  document.getElementById('history-overlay')?.remove();
  const overlay = document.createElement('div');
  overlay.id = 'history-overlay';
  overlay.style.position = 'fixed';
  overlay.style.inset = '0';
  overlay.style.background = 'rgba(0,0,0,0.5)';
  overlay.style.display = 'grid';
  overlay.style.placeItems = 'center';
  overlay.innerHTML = `
    <div class="card" style="max-width:720px; width:92vw; max-height:80vh; overflow:auto">
      <div class="row" style="justify-content:space-between">
        <h3 style="margin:0">${t.history}</h3>
        <div class="row">
          ${matches.length ? `<button id="btn-clear">${t.clearHistory}</button>` : ''}
          <button id="btn-close">${t.close}</button>
        </div>
      </div>
      ${matches.length ? `
      <ul style="margin-top:12px">
        ${matches.map(m=>`
          <li class="row" style="justify-content:space-between; border-bottom:1px solid var(--divider); padding:8px 0">
            <div>
              <div style="font-weight:600">${m.titleA} vs ${m.titleB}</div>
              <small>${new Date(m.time).toLocaleString()}</small>
              ${m.result? `<div><small>${t.result}: ${m.result}</small></div>`:''}
            </div>
            <div class="row">
              <button data-action="share" data-id="${m.id}">${t.share}</button>
              <button data-action="view" data-id="${m.id}">Ver</button>
              <button class="danger" data-action="delete" data-id="${m.id}">${t.delete}</button>
            </div>
          </li>
        `).join('')}
      </ul>
      ` : `<p style="margin-top:12px">(vacío)</p>`}
    </div>
  `;
  document.body.appendChild(overlay);
  const doClose = ()=>{ try { document.body.removeChild(overlay); } catch {} };
  overlay.addEventListener('click', (e)=>{ if (e.target===overlay) doClose(); }, { once: true });
  const btnClose = overlay.querySelector('#btn-close');
  btnClose?.addEventListener('click', doClose, { once: true });
  btnClose?.addEventListener('touchend', (e)=>{ try { e.preventDefault(); } catch {} doClose(); }, { passive: false, once: true });
  overlay.querySelector('#btn-clear')?.addEventListener('click', ()=>{ if (confirm('¿Limpiar historial?')){ clearAllMatches(); doClose(); openHistoryModal(); }});
  overlay.querySelectorAll('button[data-action]')?.forEach(btn=>{
    btn.addEventListener('click', ()=>{
      const id = String(btn.getAttribute('data-id') || '');
      const action = btn.getAttribute('data-action');
      if (action==='delete'){
        if (confirm('¿Eliminar partido?')){ deleteMatch(id); doClose(); openHistoryModal(); }
      } else if (action==='view'){
        const m = loadMatches().find(x=>x.id===id);
        if (!m) return;
        const text = formatSavedMatchText(m);
        const res = prompt(`${text}\n\n${t.result}:`, m.result||'');
        if (res!==null){ updateMatchResult(id, res.trim()); doClose(); openHistoryModal(); }
      } else if (action==='share'){
        const m = loadMatches().find(x=>x.id===id);
        if (!m) return;
        const text = formatSavedMatchText(m);
        (async ()=>{
          try {
            if (navigator.share){ await navigator.share({ text }); }
            else { await navigator.clipboard.writeText(text); alert('Copiado al portapapeles'); }
          } catch {}
        })();
      }
    });
  });
}

function openEditPlayersDialog(){
  const t = i18n();
  const statePlayers = loadPlayers();
  let players = statePlayers.slice();
  // ensure only one edit overlay exists
  document.getElementById('edit-overlay')?.remove();
  const overlay = document.createElement('div');
  overlay.id = 'edit-overlay';
  overlay.style.position = 'fixed';
  overlay.style.inset = '0';
  overlay.style.background = 'rgba(0,0,0,0.5)';
  overlay.style.display = 'grid';
  overlay.style.placeItems = 'center';
  function renderDialog(){
    const prevScroller = overlay.querySelector('#players-editor-scroll');
    const prevScrollTop = prevScroller ? prevScroller.scrollTop : 0;
    overlay.innerHTML = `
      <div class="card" style="max-width:860px; width:96vw; max-height:86vh; overflow:auto">
        <div class="row" style="justify-content:space-between">
          <h3 style="margin:0">${t.edit}</h3>
          <div class="row">
            <button id="save-all">${t.saveChanges}</button>
            <button id="close">${t.close}</button>
          </div>
        </div>
        <div id="players-editor-scroll" style="margin-top:8px; max-height:320px; overflow:auto">
          <ul>
            ${players.slice().sort((a,b)=> a.name.localeCompare(b.name, undefined, { sensitivity:'base' })).map(p=>`
              <li style="padding:8px 0; border-bottom:1px solid var(--divider)">
                <div class="row" style="justify-content:space-between">
                  <div class="row">
                    <span>${p.isGoalkeeper?'🧤':'👕'}</span>
                    <input type="checkbox" data-gk="${p.name}" ${p.isGoalkeeper?'checked':''} />
                    <strong>${p.name}</strong>
                  </div>
                  <div class="row">
                    <button data-rename="${p.name}">${t.edit}</button>
                    <button class="danger" data-delete="${p.name}">${t.delete}</button>
                  </div>
                </div>
                <div class="row" style="margin-top:6px">
                  <label class="row" style="gap:4px"><small>${t.attack}</small>
                    <input type="text" data-a="${p.name}" value="${p.attack}" style="width:90px" />
                  </label>
                  <label class="row" style="gap:4px"><small>${t.defense}</small>
                    <input type="text" data-d="${p.name}" value="${p.defense}" style="width:90px" />
                  </label>
                  <label class="row" style="gap:4px"><small>${t.physical}</small>
                    <input type="text" data-p="${p.name}" value="${p.physical}" style="width:90px" />
                  </label>
                </div>
              </li>
            `).join('')}
          </ul>
        </div>
        <div style="margin-top:12px; border-top:1px solid var(--divider); padding-top:12px">
          <h4 style="margin:0 0 8px">Añadir jugador</h4>
          <div class="row">
            <input type="text" id="new-name" placeholder="Nombre" style="flex:1" />
            <input type="text" id="new-a" placeholder="${t.attack}" style="width:100px" />
            <input type="text" id="new-d" placeholder="${t.defense}" style="width:100px" />
            <input type="text" id="new-p" placeholder="${t.physical}" style="width:100px" />
            <label class="row"><input type="checkbox" id="new-gk" /> GK</label>
            <button id="add-player">Agregar</button>
          </div>
        </div>
      </div>
    `;
    const newScroller = overlay.querySelector('#players-editor-scroll');
    if (newScroller && typeof prevScrollTop === 'number') newScroller.scrollTop = prevScrollTop;
  }
  renderDialog();
  document.body.appendChild(overlay);
  // Bind events for the initial render so buttons work immediately
  bindEditDialogEvents();
  overlay.addEventListener('click', (e)=>{ if (e.target===overlay) document.body.removeChild(overlay); });

  function bindEditDialogEvents(){
    const doClose = ()=>{ try { document.body.removeChild(overlay); } catch {} };
    overlay.querySelector('#close')?.addEventListener('click', doClose, { once: true });
    overlay.querySelector('#close')?.addEventListener('touchend', (e)=>{ e.preventDefault?.(); doClose(); }, { passive: false, once: true });
    overlay.querySelector('#save-all')?.addEventListener('click', ()=>{
      const aInputs = overlay.querySelectorAll('input[data-a]');
      aInputs.forEach(inp=>{
        const name = inp.getAttribute('data-a');
        const p = players.find(x=>x.name===name);
        if (!p) return;
        // accept comma decimals and trim
        const parseNum = (s)=>{ const v = String(s||'').trim().replace(',', '.'); const n = parseFloat(v); return n; };
        const a = parseNum(inp.value);
        const d = parseNum(overlay.querySelector(`input[data-d="${name}"]`).value);
        const phys = parseNum(overlay.querySelector(`input[data-p="${name}"]`).value);
        if ([a,d,phys].every(v=>!isNaN(v) && v>=1 && v<=10)){
          p.attack = a; p.defense = d; p.physical = phys;
        }
        const gkCb = overlay.querySelector(`input[data-gk="${name}"]`);
        p.isGoalkeeper = gkCb && gkCb.checked ? true : false;
      });
      savePlayers(players);
      try { document.body.removeChild(overlay); } catch {}
      init();
    });
    overlay.querySelectorAll('button[data-rename]')?.forEach(btn=>{
      btn.addEventListener('click', ()=>{
        const name = btn.getAttribute('data-rename');
        const p = players.find(x=>x.name===name);
        if (!p) return;
        const v = prompt('Nuevo nombre', p.name) || '';
        const t2 = v.trim();
        if (!t2) return;
        if (players.some(x=>x!==p && x.name.toLowerCase()===t2.toLowerCase())) return;
        p.name = t2;
        renderDialog();
        bindEditDialogEvents();
      });
    });
    overlay.querySelectorAll('button[data-delete]')?.forEach(btn=>{
      btn.addEventListener('click', ()=>{
        const name = btn.getAttribute('data-delete');
        players = players.filter(x=>x.name!==name);
        renderDialog();
        bindEditDialogEvents();
      });
    });
    overlay.querySelector('#add-player')?.addEventListener('click', ()=>{
      const name = (overlay.querySelector('#new-name').value || '').trim();
      const parseNum = (s)=>{ const v = String(s||'').trim().replace(',', '.'); const n = parseFloat(v); return n; };
      const a = parseNum(overlay.querySelector('#new-a').value);
      const d = parseNum(overlay.querySelector('#new-d').value);
      const phys = parseNum(overlay.querySelector('#new-p').value);
      const gk = !!overlay.querySelector('#new-gk').checked;
      if (!name || players.some(p=>p.name.toLowerCase()===name.toLowerCase())) return;
      if ([a,d,phys].some(v=>isNaN(v) || v<1 || v>10)) return;
      players.push({ name, attack:a, defense:d, physical:phys, isGoalkeeper:gk });
      // persist immediately so the addition is effective without requiring "Aplicar Cambios"
      savePlayers(players);
      renderDialog();
      bindEditDialogEvents();
    });
  }

  bindEditDialogEvents();
}

function openCustomizeDialog(){
  const t = i18n();
  const players = loadPlayers();
  const overlay = document.createElement('div');
  overlay.style.position = 'fixed';
  overlay.style.inset = '0';
  overlay.style.background = 'rgba(0,0,0,0.5)';
  overlay.style.display = 'grid';
  overlay.style.placeItems = 'center';
  const assign = Object.fromEntries(players.map(p=>[p.name,'N']));
  overlay.innerHTML = `
    <div class="card" style="max-width:860px; width:96vw; max-height:86vh; overflow:auto">
      <div class="row" style="justify-content:space-between">
        <h3 style="margin:0">${t.create}</h3>
        <div class="row">
          <button id="apply">${t.create}</button>
          <button id="close">${t.close}</button>
        </div>
      </div>
      <div style="margin-top:8px; max-height:420px; overflow:auto">
        <ul>
          ${players.map(p=>`
            <li class="row" style="justify-content:space-between; border-bottom:1px solid var(--divider); padding:6px 0">
              <span>${p.isGoalkeeper?'🧤':'👕'} ${p.name}${p.isGoalkeeper?' (GK)':''}</span>
              <span class="row">
                <label class="row"><input type="radio" name="r-${p.name}" data-n="${p.name}" checked /> N</label>
                <label class="row"><input type="radio" name="r-${p.name}" data-a2="${p.name}" /> A</label>
                <label class="row"><input type="radio" name="r-${p.name}" data-b2="${p.name}" /> B</label>
              </span>
            </li>
          `).join('')}
        </ul>
      </div>
    </div>
  `;
  document.body.appendChild(overlay);
  overlay.addEventListener('click', (e)=>{ if (e.target===overlay) document.body.removeChild(overlay); });
  overlay.querySelector('#close')?.addEventListener('click', ()=> document.body.removeChild(overlay));
  overlay.querySelectorAll('input[data-n]')?.forEach(inp=> inp.addEventListener('change', ()=>{ assign[inp.getAttribute('data-n')] = 'N'; }));
  overlay.querySelectorAll('input[data-a2]')?.forEach(inp=> inp.addEventListener('change', ()=>{ assign[inp.getAttribute('data-a2')] = 'A'; }));
  overlay.querySelectorAll('input[data-b2]')?.forEach(inp=> inp.addEventListener('change', ()=>{ assign[inp.getAttribute('data-b2')] = 'B'; }));
  overlay.querySelector('#apply')?.addEventListener('click', ()=>{
    const a = players.filter(p=>assign[p.name]==='A');
    const b = players.filter(p=>assign[p.name]==='B');
    const state = {
      players,
      selected: new Set(players.map(p=>p.name)),
      count: Math.min(10, Math.max(2, players.length)),
      teamA: a,
      teamB: b,
      showResults: true,
      teamATitle: i18n().teamA,
      teamBTitle: i18n().teamB
    };
    document.body.removeChild(overlay);
    render(state);
  });
}

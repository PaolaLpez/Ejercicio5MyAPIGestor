import http from 'k6/http';
import { check, sleep } from 'k6';

// Configuracion de Escenario de Carga conforme a Rubrica de Evaluacion
export const options = {
  stages: [
    { duration: '30s', target: 20 }, // Rampa de subida a 20 usuarios
    { duration: '1m',  target: 50 }, // Carga sostenida a 50 usuarios concurrentes
    { duration: '30s', target: 0  }, // Rampa de bajada
  ],
  thresholds: {
    http_req_duration: ['p(95)<400'], // 95% de peticiones deben responder en menos de 400ms
    http_req_failed: ['rate<0.01'],   // Menos del 1% de errores permitidos
  },
};

const BASE_URL_FRONT = 'http://localhost:8080';
const BASE_URL_API   = 'http://localhost:8081';

export default function () {
  // Caso 1: Carga de Vista Inicio Frontend
  let resFront = http.get(`${BASE_URL_FRONT}/inicio`);
  check(resFront, {
    'Frontend /inicio status 200': (r) => r.status === 200,
    'Frontend tiempo < 300ms': (r) => r.timings.duration < 300,
  });

  // Caso 2: Consulta de Proyectos API REST Backend
  let payload = JSON.stringify({});
  let params = {
    headers: { 'Content-Type': 'application/json' },
  };
  let resApi = http.post(`${BASE_URL_API}/buscarProyectos`, payload, params);
  check(resApi, {
    'API /buscarProyectos status 200': (r) => r.status === 200,
    'API respuesta valida': (r) => r.body.length > 0,
  });

  sleep(1);
}

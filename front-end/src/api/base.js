export const URL = "http://localhost:8080";

const baseRequestSetting = {
  mode: "cors",
};

export async function httpGet(url) {
  return await fetch(url, {
    ...baseRequestSetting,
    method: "GET",
  });
}

export async function httpPost(url, body) {
  return await fetch(url, {
    ...baseRequestSetting,
    body: JSON.stringify(body),
    headers: {
      "content-type": "application/json",
    },
    method: "POST",
  });
}

export async function httpDelete(url) {
  return await fetch(url, {
    ...baseRequestSetting,
    method: "DELETE",
  });
}

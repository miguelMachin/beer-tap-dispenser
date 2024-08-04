import { DispenserSpending, Dispenser, DispenserStatus } from "../types/types";

export const URL_BASE = "http://localhost:8080/dispenser"

export const requestWrapper = (method: string, url: string, data = {}): Promise<any> => {
	const headers: Headers = new Headers();

	headers.append("Content-type", "application/json; charset=UTF-8")
	const params: RequestInit = {
		method,
		headers,
		mode: "cors",
	}
	if (method === "POST" || method === "PUT") {
		params.body = JSON.stringify(data)
	}

	return fetch(url, params)
};


export function getDispenser(dispenserId: string): Promise<DispenserSpending> {
	const url = `${URL_BASE}/${dispenserId}`;
	const method = "GET"


	return requestWrapper(method, url)
		.then((res) => res.json())
		.then((data) => {
			return data
		})
		.catch((e) => { console.log(e) })
}

export function getDispensers(): Promise<Dispenser[]> {
	const url = URL_BASE
	const method = "GET"

	return requestWrapper(method, url)
		.then((res) => res.json())
		.then((data) => {
			return data
		})
		.catch((e) => { console.log(e) })
}

export function createDispenser(flowVolume: number, pricePerLiter: number): Promise<Dispenser> {
	const url = URL_BASE
	const method = "POST"
	const data = {
		flow_volume: flowVolume,
		price_per_liter: pricePerLiter
	}

	return requestWrapper(method, url, data)
		.then((res) => res.json())
		.then((data) => {
			return data
		})
		.catch((e) => { console.log(e) })

}

export function updateStatusDispenser(id: string, status: number): Promise<DispenserStatus> {
	const url = `${URL_BASE}/${id}/status`
	const method = "PUT"
	const data = {
		"status": status,
		"updated_at": parseDate()
	}


	return requestWrapper(method, url, data)
		.then((res) => res.json())
		.then((data) => {
			return data
		})
		.catch((e) => { console.error(e) })

}

function parseDate(): string {
	return new Date().toJSON().replace(/\.\d+/, "");
}

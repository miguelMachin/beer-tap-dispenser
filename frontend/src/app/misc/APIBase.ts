import { DispenserSpending, Dispenser } from "../commons/interfaces";

export const URL_BASE = "http://localhost:8080/dispenser"

export const requestWrapper = (method: string, url: string, data = {}): Promise<any>  => {
	const headers: Headers = new Headers();

    headers.append("Content-type", "application/json; charset=UTF-8")
    const params: RequestInit = {
		method,
		headers,
		mode: "cors",
	}
	if (method === "POST") {
		params.body = JSON.stringify(data)
	}
	console.log(params)

	return fetch(url, params)
};


export function getDispenser(dispenserId: string): Promise<DispenserSpending>  {
    const url = URL_BASE + "/f0d70417-6cb3-4118-b618-219463044586/spending"
	const method = "GET"

	 
	return requestWrapper(method, url )
	.then((res) => res.json())
	.then((data) => {
		return data
	})
	.catch((e) => { console.log(e)})
}

export function getDispensers(): Promise<Dispenser[]>  {
    const url = URL_BASE
	const method = "GET"
	 
	return requestWrapper(method, url )
	.then((res) => res.json())
	.then((data) => {
		return data
	})
	.catch((e) => { console.log(e)})
}

export function createDispenser(flowVolume: number, pricePerLiter: number): Promise<Dispenser>  {
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
	.catch((e) => { console.log(e)})

}

'use client'
import { Inter } from "next/font/google";
import { redirect } from 'next/navigation'

const inter = Inter({ subsets: ['latin'] })

export default function Home() {
    const handlerSubmit = () => {
        window.localStorage.setItem('user', "admin");
        window.localStorage.setItem('password', "admin");
    }
    
    return (
        <main className={inter.className}>
            <section className="container">
                <div className="form">
                    <fieldset>
                        <div className="center">
                           Login
                        </div>
                        <div className="form-group">
                            <input type="text" className="form-control" placeholder="Username" defaultValue={"admin"}/>
                        </div>
                        <div className="form-group">
                            <input type="password" className="form-control" placeholder="password" defaultValue={"admin"}/>
                        </div>
                        <div className="center">
                            <a href="/admin" 
                                className="btn btn-primary" onClick={() => handlerSubmit()}>Submit</a>
                        </div>
                    </fieldset>

                </div>

            </section>
        </main>
    );
}

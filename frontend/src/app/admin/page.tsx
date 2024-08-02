import { Inter } from "next/font/google";
import TableDispender from "./../components/TableDispender/TableDispender"

const inter = Inter({ subsets: ['latin'] })

export default function Home() {
  return (
    <main className={inter.className}>
      <section id="container">
        <TableDispender />
      </section>
    </main>
  );
}

import { Inter } from "next/font/google";
import Dispenser from "./components/Dispender/Dispenser";

const inter = Inter({subsets: ['latin']})

export default function Home() {
  return (
    <main className={inter.className}>
      <div> Hello </div>
      <section>
        <Dispenser />
      </section>
    </main>
  );
}

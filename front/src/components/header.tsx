import { Link } from "react-router-dom";

export const Header = () => {
    return (
        <header className="w-screen bg-blue-400 px-4 py-5 flex justify-between items-center">
            <div className="flex items-center">
                <img src="/vite.svg" alt="logo" className="mr-2 w-16 h-16"/>
                <h1 className="font-bold text-4xl">Empresa</h1>
            </div>
            <ul className="grid grid-flow-col gap-x-4 justify-content">
                <Link to={"/"}><a>Listagem de Pessoas</a></Link>
                <Link to={"form"}><a>Novo cadastro</a></Link>
            </ul>
        </header>
    )
};
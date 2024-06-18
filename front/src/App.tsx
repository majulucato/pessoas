import { Route, createBrowserRouter, createRoutesFromElements, RouterProvider } from "react-router-dom"
import { ChakraProvider } from "@chakra-ui/react";
import './App.css'
import PersonList from "./components/person-list";
import PersonForm from "./components/person-form";
import ContactList from "./components/contactsModal";
import PersonEdit from "./components/personEdit";

function App() {
  const router = createBrowserRouter(
    createRoutesFromElements(
      <>
        <Route path="/" element={<PersonList />} />
        <Route path="/form" element={<PersonForm/>} />
        <Route path="/contacts/:id" element={<ContactList {...[]}/>} />
      </>
    )
  )
  return (
    <>
      <ChakraProvider>
        <RouterProvider router={router} />
      </ChakraProvider>
    </>
  );
}


export default App;


const express = require("express");
const router = express.Router();
const Web3 = require("web3");
const web3 = new Web3("http://127.0.0.1:8545");
const contractAddress = "0x30475a48cecdc7bf0263e8021513e9e099d12f4c";
const contractABI = [
  {
    constant: true,
    inputs: [],
    name: "totalSupply",
    outputs: [
      {
        name: "",
        type: "uint256"
      }
    ],
    payable: false,
    stateMutability: "view",
    type: "function"
  },
  {
    constant: true,
    inputs: [
      {
        name: "tokenOwner",
        type: "address"
      }
    ],
    name: "balanceOf",
    outputs: [
      {
        name: "balance",
        type: "uint256"
      }
    ],
    payable: false,
    stateMutability: "view",
    type: "function"
  },
  {
    constant: false,
    inputs: [
      {
        name: "to",
        type: "address"
      },
      {
        name: "tokens",
        type: "uint256"
      }
    ],
    name: "transfer",
    outputs: [
      {
        name: "success",
        type: "bool"
      }
    ],
    payable: false,
    stateMutability: "nonpayable",
    type: "function"
  },
  {
    anonymous: false,
    inputs: [
      {
        indexed: true,
        name: "from",
        type: "address"
      },
      {
        indexed: true,
        name: "to",
        type: "address"
      },
      {
        indexed: false,
        name: "tokens",
        type: "uint256"
      }
    ],
    name: "Transfer",
    type: "event"
  }
];

const contract = new web3.eth.Contract(contractABI, contractAddress);
router.get("/totalSupply", (req, res) => {
  contract.methods
    .totalSupply()
    .call()
    .then(function(sup) {
      if (!sup) {
        res.status(404);
      }
      return res.status(200).json(web3.utils.toBN(sup).toString());
    });
});
function getBalance(address, req, res) {
  contract.methods
    .balanceOf(address)
    .call()
    .then(function(bal) {
      if (!bal) {
        return res.status(404);
      }
      return res.status(200).json(web3.utils.toBN(bal).toString());
    });
}
router.post("/balanceOf", (req, res) => {
  getBalance(req.body.address, req, res);
});
// function transfer(recieve, send, token) {
// }
router.post("/transfer", (req, res) => {
  const recieve = req.body.reciever;
  const send = req.body.sender;
  const token = req.body.token;
  (recieve, send, token) => {
    contract.methods
      .transfer(recieve, token)
      .send({ from: send })
      .then(function(tx) {
        console.log(tx);
      })
      .catch(function(tx) {
        console.log(tx);
      });
  };
  return res.status(200);
});

module.exports = router;
